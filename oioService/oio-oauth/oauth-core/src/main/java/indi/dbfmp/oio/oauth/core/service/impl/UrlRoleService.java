package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.UrlRoleServiceTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * url角色服务
 * </p>
 *
 * @author dbfmp
 * @name: UrlRoleService
 * @since 2020/11/11 9:51 下午
 */
@Slf4j
@Service
public class UrlRoleService {

    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IGroupRoleInnerService groupRoleInnerService;
    @Autowired
    private IUrlRoleInnerService urlRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;
    @Autowired
    private UrlRoleServiceTransaction urlRoleServiceTransaction;
    @Autowired
    private IRolesInnerService rolesInnerService;

    /**
     * 根据group分组授权url
     *
     * @param url     url
     * @param groupId 分组ID
     * @return 是否授权成功
     */
    public boolean grantRolesToUrlByGroup(String url, String groupId) {
        //group正确性
        Groups queryGroup = groupsInnerService.getById(groupId);
        if (null == queryGroup) {
            log.warn("无效的groupId");
            return false;
        }
        Org queryOrg = orgInnerService.getById(queryGroup.getOrgId());
        //查询group对应的角色
        List<GroupRole> groupRoles = groupRoleInnerService.lambdaQuery().eq(GroupRole::getGroupId, groupId).list();
        if (CollectionUtil.isEmpty(groupRoles)) {
            return true;
        }
        List<UrlRole> urlRoleList = new ArrayList<>();
        groupRoles.forEach(groupRole -> {
            if (urlRoleInnerService.lambdaQuery().eq(UrlRole::getRoleId, groupRole.getRoleId()).count() <= 0) {
                urlRoleList.add(UrlRole.builder()
                        .orgId(queryOrg.getId())
                        .orgName(queryOrg.getOrgName())
                        .url(url)
                        .roleId(groupRole.getRoleId())
                        .roleName(groupRole.getRoleName())
                        .build());
            }
        });
        List<UrlPermission> urlPermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(urlRoleList)) {
            urlRoleList.forEach(urlRole -> {
                List<RolePermission> rolePermissionList = rolePermissionInnerService.lambdaQuery().eq(RolePermission::getRoleId, urlRole.getRoleId()).list();
                if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                    rolePermissionList.forEach(rolePermission -> {
                        if (urlPermissionInnerService.lambdaQuery().eq(UrlPermission::getUrl, url).eq(UrlPermission::getPermissionId, rolePermission.getPermissionId()).count() <= 0) {
                            urlPermissionList.add(UrlPermission.builder()
                                    .orgId(queryOrg.getId())
                                    .orgName(queryOrg.getOrgName())
                                    .permissionName(rolePermission.getPermissionId())
                                    .permissionName(rolePermission.getPermissionName())
                                    .url(url)
                                    .build());
                        }
                    });
                }
            });
        }
        try {
            urlRoleServiceTransaction.grantRolesToUrlByGroup(urlRoleList, urlPermissionList);
            return true;
        } catch (Exception e) {
            log.error("事务授权url角色权限失败！", e);
            return false;
        }
    }

    /**
     * 授权角色权限到url
     *
     * @param url    url
     * @param roleId 角色ID
     * @return 是否授权成功
     */
    public boolean grantRolesToUrl(String url, String roleId) {
        Roles queryRole = rolesInnerService.getById(roleId);
        if (null == queryRole) {
            log.warn("role无效！roleId:{}", roleId);
            return false;
        }
        Org queryOrg = orgInnerService.getById(queryRole.getOrgId());
        UrlRole urlRole = urlRoleInnerService.lambdaQuery().eq(UrlRole::getUrl, url).eq(UrlRole::getRoleId, roleId).one();
        if (null == urlRole) {
            urlRole = UrlRole.builder()
                    .roleId(queryRole.getId())
                    .roleName(queryRole.getRoleName())
                    .orgId(queryOrg.getId())
                    .orgName(queryOrg.getOrgName())
                    .url(url)
                    .build();
        }
        //查询权限
        List<UrlPermission> urlPermissionList = new ArrayList<>();
        List<RolePermission> rolePermissionList = rolePermissionInnerService.lambdaQuery().eq(RolePermission::getRoleId, roleId).list();
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            rolePermissionList.forEach(rolePermission -> {
                if (urlPermissionInnerService.lambdaQuery().eq(UrlPermission::getPermissionId, rolePermission.getPermissionId()).eq(UrlPermission::getUrl, url).count() <= 0) {
                    urlPermissionList.add(UrlPermission.builder()
                            .url(url)
                            .permissionId(rolePermission.getPermissionId())
                            .permissionName(rolePermission.getPermissionName())
                            .orgId(queryOrg.getId())
                            .orgName(queryOrg.getOrgName())
                            .build());
                }
            });
        }
        try {
            urlRoleServiceTransaction.grantRolesToUrl(urlRole,urlPermissionList);
            return true;
        }catch (Exception e) {
            log.error("事务授权url角色失败！", e);
            return false;
        }
    }

}
