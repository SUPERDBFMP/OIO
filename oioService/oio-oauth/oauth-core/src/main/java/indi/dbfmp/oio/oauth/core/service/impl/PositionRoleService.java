package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.PositionRoleServiceTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  角色权限服务
 * </p>
 *
 * @author dbfmp
 * @name: PositionRoleService
 * @since 2020/10/27 10:36 下午
 */
@Slf4j
@Service
public class PositionRoleService {

    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUserPositionInnerService userPositionInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private PositionRoleServiceTransaction positionRoleServiceTransaction;

    /**
     * 添加新的角色到职位中
     *
     * 添加完成后，原有通过职位分组授权角色，权限的用户对应的数据会相应更新
     *
     * @param groupId 分组id
     * @param positionId 职位id
     * @param roleIdList   角色id列表
     * @return 是否添加成功
     */
    public boolean addNewRoleToPosition(String groupId, String positionId,List<String> roleIdList) {
        log.info("addNewRoleToPosition,groupId:{};positionId:{};roleIdList:{}",groupId,positionId,roleIdList);
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
        //检查职位合法性
        PositionGroup queryPositionGroup = positionGroupInnerService.getOne(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId,groupId).eq(PositionGroup::getPositionId,positionId));
        if (null == queryPositionGroup) {
            log.warn("无效的职位id");
            return false;
        }
        //检查roleIdList合法性
        List<Roles> rolesList = rolesInnerService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, roleIdList).eq(Roles::getOrgId, queryOrg.getId()));
        if (rolesList.size() != roleIdList.size()) {
            log.warn("存在无效的RoleId");
            return false;
        }
        //查询角色下的权限
        List<RolePermission> rolePermissionList = rolePermissionInnerService.list(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getGroupId,groupId)
                .in(RolePermission::getRoleId,roleIdList));
        List<String> permissionIdList = null;
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }
        //查询此职位下的用户
        List<UserPosition> userPositionsList = userPositionInnerService.list(new LambdaQueryWrapper<UserPosition>().eq(UserPosition::getPositionId, positionId).eq(UserPosition::getGroupId, groupId));
        List<String> userIdList = new ArrayList<>();
        List<UserRole> userRoleList = new ArrayList<>();
        List<UserPermission> userPermissionsList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userPositionsList)) {
            userIdList = userPositionsList.stream().map(UserPosition::getUserId).collect(Collectors.toList());
            userIdList.forEach(userId ->{
                rolesList.forEach(role -> {
                    if (userRoleInnerService.count(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, role.getId())) <= 0) {
                        userRoleList.add(UserRole.builder()
                                .groupId(groupId)
                                .groupName(queryGroup.getGroupName())
                                .userId(userId)
                                .roleId(role.getId())
                                .roleName(role.getRoleName())
                                .build());
                    }
                });
            });
        }
        if (CollectionUtil.isNotEmpty(permissionIdList)) {
            userIdList.forEach(userId ->{
                rolePermissionList.forEach(rolePermission -> {
                    if (userPermissionInnerService.count(new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getUserId, userId).eq(UserPermission::getPermissionId, rolePermission.getId())) <= 0) {
                        userPermissionsList.add(UserPermission.builder()
                                .groupId(groupId)
                                .groupName(queryGroup.getGroupName())
                                .userId(userId)
                                .permissionName(rolePermission.getPermissionName())
                                .permissionId(rolePermission.getPermissionId())
                                .build());
                    }
                });
            });
        }
        List<PositionRole> positionRoleList = new ArrayList<>();
        rolesList.forEach(roles -> {
            if (positionRoleInnerService.count(new LambdaQueryWrapper<PositionRole>().eq(PositionRole::getGroupId,queryGroup.getId()).eq(PositionRole::getPositionId,positionId).eq(PositionRole::getRoleId,roles.getId())) <= 0) {
                positionRoleList.add(PositionRole.builder()
                        .groupName(queryGroup.getGroupName())
                        .groupId(queryGroup.getId())
                        .roleName(roles.getRoleName())
                        .roleId(roles.getId())
                        .positionId(positionId)
                        .positionName(queryPositionGroup.getPositionName())
                        .build());
            }
        });
        try {
            positionRoleServiceTransaction.addRolesToPosition(positionRoleList, userRoleList, userPermissionsList);
            return true;
        } catch (Exception e) {
            log.error("事务addRolesToPosition执行失败！",e);
            return false;
        }

    }

    /**
     * 从职位中移除角色
     *
     * 移除完成后，原有通过职位分组授权角色，权限的用户对应的数据会相应更新
     *
     * @param groupId 分组id
     * @param positionId 职位id
     * @param roleIdList 角色id列表
     * @return 是否移除成功
     */
    public boolean removeRolesFromPosition(String groupId, String positionId,List<String> roleIdList) {
        log.info("removeRolesFromPosition,groupId:{};positionId:{};roleIdList:{}",groupId,positionId,roleIdList);
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
        //检查职位合法性
        PositionGroup queryPositionGroup = positionGroupInnerService.getOne(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId,groupId).eq(PositionGroup::getPositionId,positionId));
        if (null == queryPositionGroup) {
            log.warn("无效的职位id");
            return false;
        }
        //检查roleIdList合法性
        List<Roles> rolesList = rolesInnerService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, roleIdList).eq(Roles::getOrgId, queryOrg.getId()));
        if (rolesList.size() != roleIdList.size()) {
            log.warn("存在无效的RoleId");
            return false;
        }
        //查询角色下的权限
        List<RolePermission> rolePermissionList = rolePermissionInnerService.list(new LambdaQueryWrapper<RolePermission>()
                .eq(RolePermission::getGroupId,groupId)
                .in(RolePermission::getRoleId,roleIdList));
        List<String> permissionIdList = null;
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }
        //查询对应的用户角色
        List<String> userRoleIdList = null;
        List<UserRole> userRoleList = userRoleInnerService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getGroupId,groupId).in(UserRole::getRoleId,roleIdList));
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleIdList = userRoleList.stream().map(UserRole::getId).collect(Collectors.toList());
        }
        //查询用户权限
        List<String> userPermissionIdList = null;
        if (CollectionUtil.isNotEmpty(permissionIdList)) {
            List<UserPermission> userPermissionList = userPermissionInnerService.list(new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getGroupId, groupId).in(UserPermission::getPermissionId, permissionIdList));
            if (CollectionUtil.isNotEmpty(userPermissionList)) {
                userPermissionIdList = userPermissionList.stream().map(UserPermission::getId).collect(Collectors.toList());
            }
        }
        try {
            positionRoleServiceTransaction.removeRolesFromPosition(groupId, positionId, roleIdList, userRoleIdList, userPermissionIdList);
            return true;
        } catch (Exception e) {
            log.error("事务removeRolesFromPosition执行失败！",e);
            return false;
        }
    }

}
