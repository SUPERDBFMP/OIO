package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.RolePermissionServiceTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: RolePermissionService
 * @since 2020/10/28 10:00 下午
 */
@Slf4j
@Service
public class RolePermissionService {

    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private IPermissionInnerService permissionInnerService;
    @Autowired
    private RolePermissionServiceTransaction rolePermissionServiceTransaction;

    /**
     * 添加新的角色到职位中
     *
     * 添加完成后，原有通过职位分组授权角色，权限的用户对应的数据会相应更新
     *
     * @param roleId 角色id
     * @param permissionIdList 权限id列表
     * @return 是否添加成功
     */
    public boolean addNewPermissionToRole(String roleId, List<String> permissionIdList) {
        log.info("addNewPermissionToRole;roleId:{};permissionIdList:{}",roleId,permissionIdList);
        //检查role
        Roles queryRole = rolesInnerService.getById(roleId);
        if (null == queryRole) {
            log.warn("无效的角色,roleId:{}",roleId);
            return false;
        }
        //检查permissionIdList合法性
        List<Permission> permissionList = permissionInnerService.list(permissionInnerService.lambdaQuery().eq(Permission::getOrgId, queryRole.getOrgId()).in(Permission::getId, permissionIdList));
        if (permissionIdList.size() != permissionList.size()) {
            log.warn("存在无效的权限ID");
            return false;
        }
        //构造rolePermission
        List<RolePermission> rolePermissionList = new ArrayList<>(permissionIdList.size());
        permissionList.forEach(permission -> {
            if(rolePermissionInnerService.count(rolePermissionInnerService.lambdaQuery()
                    .eq(RolePermission::getRoleId,roleId)
                    .eq(RolePermission::getPermissionId,permission.getId()))
                    <= 0)
            rolePermissionList.add(RolePermission.builder()
                .orgId(queryRole.getOrgId())
                .orgName(queryRole.getOrgName())
                .roleId(roleId)
                .roleName(queryRole.getRoleName())
                .permissionId(permission.getId())
                .permissionName(permission.getPermissionName())
                .build());
        });
        //查询有此角色的用户
        List<UserRole> userRoleList = userRoleInnerService.list(userRoleInnerService.lambdaQuery().eq(UserRole::getRoleId,roleId));
        List<UserPermission> userPermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleList.forEach(userRole -> {
                permissionList.forEach(permission -> {
                    if (userPermissionInnerService.count(new LambdaQueryWrapper<UserPermission>()
                            .eq(UserPermission::getUserId,userRole.getUserId())
                            .eq(UserPermission::getPermissionId,permission.getId())) <= 0) {
                        userPermissionList.add(UserPermission.builder()
                                .orgId(queryRole.getOrgId())
                                .orgName(queryRole.getOrgName())
                                .permissionId(permission.getId())
                                .permissionName(permission.getPermissionName())
                                .userId(userRole.getUserId())
                                .build());
                    }
                });
            });
        }
        try {
            rolePermissionServiceTransaction.addNewPermissionToRole(rolePermissionList, userPermissionList);
            return true;
        } catch (Exception e) {
            log.error("事务addNewPermissionToRole执行失败！",e);
            return false;
        }
    }

    /**
     * 把权限从角色中移除
     * @param roleId 角色id
     * @param permissionIdList 权限id列表
     * @return 是否成功
     */
    public boolean removePermissionFromRole(String roleId, List<String> permissionIdList){
        log.info("removePermissionFromRole;roleId:{};permissionIdList:{}",roleId,permissionIdList);
        //检查role
        Roles queryRole = rolesInnerService.getById(roleId);
        if (null == queryRole) {
            log.warn("无效的角色,roleId:{}",roleId);
            return false;
        }
        //检查permissionIdList合法性
        List<Permission> permissionList = permissionInnerService.list(permissionInnerService.lambdaQuery().in(Permission::getId, permissionIdList));
        if (permissionIdList.size() != permissionList.size()) {
            log.warn("存在无效的权限ID");
            return false;
        }
        try {
            rolePermissionServiceTransaction.removePermissionFromRole(roleId, permissionIdList);
            return true;
        } catch (Exception e) {
            log.error("事务removePermissionFromRole执行失败！",e);
            return false;
        }

    }


}
