package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.GroupRoleServiceTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  分组角色服务
 * </p>
 *
 * @author dbfmp
 * @name: GroupRoleService
 * @since 2020/10/27 8:58 下午
 */
@Slf4j
@Service
public class GroupRoleService {

    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private IGroupRoleInnerService groupRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private GroupRoleServiceTransaction groupRoleServiceTransaction;

    /**
     * 添加新的角色到分组中
     *
     * 添加完成后，原有通过group授权角色，权限的用户对应的数据不会作出改变
     *
     * @param groupId 分组id
     * @param roleIdList   角色id列表
     * @return 是否添加成功
     */
    public boolean addNewRoleToGroup(String groupId, List<String> roleIdList) {
        log.info("addNewRoleToGroup,groupId:{};roleIdList:{}",groupId,roleIdList);
        //检查groupId 合法性
        Groups queryGroup = groupsInnerService.getById(groupId);
        if (null == queryGroup) {
            log.warn("无效的groupId");
            return false;
        }
        //查询分组对应的org
        Org queryOrg = orgInnerService.getById(queryGroup.getOrgId());
        if (null == queryOrg) {
            log.warn("无效的组织机构");
            return false;
        }
        //检查角色id合法性
        List<Roles> rolesList = rolesInnerService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, roleIdList).eq(Roles::getOrgId, queryOrg.getId()));
        if (rolesList.size() != roleIdList.size()) {
            log.warn("存在无效的RoleId");
            return false;
        }
        //添加数据
        List<GroupRole> groupRoleList = new ArrayList<>(roleIdList.size());
        rolesList.forEach(roles -> {
            if (groupRoleInnerService.count(new LambdaQueryWrapper<GroupRole>().eq(GroupRole::getGroupId, groupId).eq(GroupRole::getRoleId, roles.getId())) <= 0) {
                groupRoleList.add(GroupRole.builder()
                        .groupId(queryGroup.getId())
                        .groupName(queryGroup.getGroupName())
                        .roleId(roles.getId())
                        .roleName(roles.getRoleName())
                        .build());
            }
        });
        return groupRoleInnerService.saveBatch(groupRoleList);
    }

    /**
     *
     * 把原有在group中的角色移除
     *
     * 原有通过group授权的用户角色，权限的分组将调整到默认分组中
     *
     * 默认分组名${orgCode}:default
     *
     * @param groupId 分组id
     * @param roleIdList 角色id列表
     * @return 是否移除成功
     */
    public boolean removeRolesFromGroup(String groupId, List<String> roleIdList) {
        log.info("removePositionFromGroup,groupId:{};roleIdList:{}",groupId,roleIdList);
        //检查groupId 合法性
        Groups queryGroup = groupsInnerService.getById(groupId);
        if (null == queryGroup) {
            log.warn("无效的groupId");
            return false;
        }
        //查询分组对应的org
        Org queryOrg = orgInnerService.getById(queryGroup.getOrgId());
        if (null == queryOrg) {
            log.warn("无效的组织机构,orgId:{}",queryGroup.getOrgId());
            return false;
        }
        //检查当前分组下是否存在此roleIdList
        if (groupRoleInnerService.count(groupRoleInnerService.lambdaQuery().eq(GroupRole::getGroupId,groupId).in(GroupRole::getRoleId,roleIdList)) != roleIdList.size()) {
            log.warn("存在不是当前分组下的角色");
            return false;
        }
        //查询角色下的权限
        List<RolePermission> rolePermissionList = rolePermissionInnerService.list(rolePermissionInnerService.lambdaQuery()
                .in(RolePermission::getRoleId,roleIdList));
        List<String> permissionIdList = null;
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }
        //查询默认分组ID
        Groups defaultGroup = groupsInnerService.getOne(groupsInnerService.lambdaQuery().eq(Groups::getOrgId,queryOrg.getId()).eq(Groups::getGroupCode,queryOrg.getOrgCode() + ":default"));
        if (null == defaultGroup) {
            log.error("未找到默认分组，请配置！org:{}",queryOrg);
            return false;
        }

        //todo 用户组查询出用户
        try {
            groupRoleServiceTransaction.removeRolesFromGroup(groupId, roleIdList, permissionIdList, defaultGroup.getId(), defaultGroup.getGroupName());
            return true;
        } catch (Exception e) {
            log.error("事务removeRolesFromGroup执行失败！",e);
            return false;
        }

    }

}
