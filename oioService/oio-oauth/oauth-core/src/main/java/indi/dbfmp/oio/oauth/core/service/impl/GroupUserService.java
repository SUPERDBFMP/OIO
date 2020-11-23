package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.update.GroupsUpdateEventListener;
import indi.dbfmp.oio.oauth.core.event.update.UserRolePermissionUpdateEvent;
import indi.dbfmp.oio.oauth.core.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.GroupUserServiceTransaction;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户组服务
 * </p>
 *
 * @author dbfmp
 * @name: GroupUserService
 * @since 2020/11/18 下午10:15
 */
@Slf4j
@Service
public class GroupUserService {

    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private IGroupUserInnerService groupUserInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IGroupRoleInnerService groupRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private GroupUserServiceTransaction groupUserServiceTransaction;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private EventService eventService;

    /**
     * 添加用户到新的用户组
     *
     * @param userId  用户ID
     * @param groupId 分组Id
     * @return 是否添加成功
     */
    public boolean saveUserToNewGroup(String userId, String groupId) {
        //检查用户有效性
        Users queryUser = usersInnerService.getById(userId);
        if (null == queryUser) {
            throw new CommonException("无此用户！");
        }
        if (StatusEnums.VALID.getCode() != queryUser.getLoginFlag()) {
            throw new CommonException("用户被封禁！无法添加用户组操作！");
        }
        //查询用户是否存在此用户组
        if (groupUserInnerService.count(groupUserInnerService.lambdaQuery().eq(GroupUser::getUserId, userId).eq(GroupUser::getGroupId, groupId)) > 0) {
            return true;
        }
        Groups queryGroup = groupsInnerService.getById(groupId);
        Org queryOrg = orgInnerService.getById(queryGroup.getOrgId());
        //组装用户组
        GroupUser groupUser = GroupUser.builder()
                .groupId(groupId)
                .groupName(queryGroup.getGroupName())
                .orgId(queryGroup.getOrgId())
                .orgName(queryGroup.getOrgName())
                .userId(userId)
                .userName(queryUser.getNickName())
                .build();
        //查询分组角色
        List<GroupRole> groupRoleList = groupRoleInnerService.list(groupRoleInnerService.lambdaQuery().eq(GroupRole::getGroupId, groupId));
        if (CollectionUtil.isEmpty(groupRoleList)) {
            return groupUserInnerService.save(groupUser);
        }
        List<UserRole> userRoleList = new ArrayList<>();
        groupRoleList.forEach(groupRole -> {
            if (userRoleInnerService.lambdaQuery().eq(UserRole::getUserId, userId).eq(UserRole::getRoleId, groupRole.getRoleId()).count() <= 0) {
                userRoleList.add(UserRole.builder()
                        .orgId(queryOrg.getId())
                        .orgName(queryOrg.getOrgName())
                        .userId(userId)
                        .userName(queryUser.getNickName())
                        .roleId(groupRole.getRoleId())
                        .roleName(groupRole.getRoleName())
                        .build());
            }
        });
        List<String> roleIdList = groupRoleList.stream().map(GroupRole::getRoleId).collect(Collectors.toList());
        //查询权限
        List<RolePermission> rolePermissions = rolePermissionInnerService.lambdaQuery().in(RolePermission::getRoleId, roleIdList).list();
        List<UserPermission> userPermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(rolePermissions)) {
            rolePermissions.forEach(rolePermission -> {
                if (userPermissionInnerService.lambdaQuery().eq(UserPermission::getUserId, userId).eq(UserPermission::getPermissionId, rolePermission.getPermissionId()).count() <= 0) {
                    userPermissionList.add(UserPermission.builder()
                            .orgId(queryOrg.getId())
                            .orgName(queryOrg.getOrgName())
                            .userId(userId)
                            .userName(queryUser.getNickName())
                            .permissionId(rolePermission.getPermissionId())
                            .permissionName(rolePermission.getPermissionName())
                            .build());
                }
            });
        }
        try {
            groupUserServiceTransaction.saveUserToNewGroup(groupUser,userRoleList,userPermissionList);
            UserRolePermissionUpdateEvent updateEvent = UserRolePermissionUpdateEvent.builder()
                    .userId(userId)
                    .build();
            Event event = eventService.createProcessingEvent(GroupsUpdateEventListener.class.getSimpleName(),JSONObject.toJSONString(updateEvent),EventTypes.UserRolePermissionUpdate);
            updateEvent.setEventId(event.getId());
            //发送更新事件
            eventPublisher.publishEvent(updateEvent);
            return true;
        }catch (Exception e) {
            log.error("事务添加用户组失败！", e);
            return false;
        }
    }

    public boolean removeUserFromGroup(String userId, String groupId) {
        //检查用户有效性
        Users queryUser = usersInnerService.getById(userId);
        if (null == queryUser) {
            throw new CommonException("无此用户！");
        }
        if (StatusEnums.VALID.getCode() != queryUser.getLoginFlag()) {
            throw new CommonException("用户被封禁！无法添加用户组操作！");
        }
        //查询用户是否存在此用户组
        if (groupUserInnerService.count(groupUserInnerService.lambdaQuery().eq(GroupUser::getUserId, userId).eq(GroupUser::getGroupId, groupId)) == 0) {
            return true;
        }
        //查询分组角色
        List<GroupRole> groupRoleList = groupRoleInnerService.list(groupRoleInnerService.lambdaQuery().eq(GroupRole::getGroupId, groupId));
        if (CollectionUtil.isEmpty(groupRoleList)) {
            //移除用户组
            groupUserInnerService.remove(groupUserInnerService.lambdaQuery().eq(GroupUser::getUserId, userId).eq(GroupUser::getGroupId, groupId));
            return true;
        }
        List<String> roleIdList = groupRoleList.stream().map(GroupRole::getRoleId).collect(Collectors.toList());
        //查询权限
        List<RolePermission> rolePermissions = rolePermissionInnerService.lambdaQuery().in(RolePermission::getRoleId, roleIdList).list();
        List<String> permissionIdList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(rolePermissions)) {
            permissionIdList = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
        }
        //删除用户分组，角色，权限
        try {
            groupUserServiceTransaction.removeUserFromGroup(userId, groupId, roleIdList, permissionIdList);
            UserRolePermissionUpdateEvent updateEvent = UserRolePermissionUpdateEvent.builder()
                    .userId(userId)
                    .build();
            Event event = eventService.createProcessingEvent(GroupsUpdateEventListener.class.getSimpleName(),JSONObject.toJSONString(updateEvent),EventTypes.UserRolePermissionUpdate);
            updateEvent.setEventId(event.getId());
            //发送更新事件
            eventPublisher.publishEvent(updateEvent);
            return true;
        } catch (Exception e) {
            log.error("事务移除用户组失败！", e);
            return false;
        }
    }

}
