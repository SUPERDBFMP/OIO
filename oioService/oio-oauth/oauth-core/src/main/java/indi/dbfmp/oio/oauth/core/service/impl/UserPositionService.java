package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.dto.UserPositionGroupDto;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.event.update.UserRolePermissionUpdateEvent;
import indi.dbfmp.oio.oauth.core.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.UserPositionServiceTransaction;
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
 *
 * </p>
 *
 * @author dbfmp
 * @name: UserPositionService
 * @since 2020/11/9 9:56 下午
 */
@Slf4j
@Service
public class UserPositionService {

    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private IPositionInnerService positionInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private UserPositionServiceTransaction userPositionServiceTransaction;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private IUserPositionInnerService userPositionInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;

    /**
     * 授予新角色给用户
     * @param userId 用户id
     * @param positionIdList 角色id列表
     * @return 是否授权成功
     */
    public boolean grantNewPositionsToUser(String userId, List<String> positionIdList) {
        //检查用户
        Users queryUser = usersInnerService.getById(userId);
        if (null != queryUser && StatusEnums.UN_VALID.getCode() != queryUser.getLoginFlag()) {
            throw new CommonException("用户被封禁，无法授权");
        }
        //查询职位
        List<Position> positionList = positionInnerService.listByIds(positionIdList);
        if (CollectionUtil.isEmpty(positionList)) {
            return true;
        }
        List<UserPosition> userPositionList = new ArrayList<>();
        positionList.forEach(position -> {
            Org queryOrg = orgInnerService.getById(position.getOrgId());
            //查询默认分组ID
            Groups defaultGroup = groupsInnerService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getOrgId,queryOrg.getId()).eq(Groups::getGroupCode,queryOrg.getOrgCode() + ":default"));
            userPositionList.add(UserPosition.builder()
                    .groupId(defaultGroup.getId())
                    .groupName(defaultGroup.getGroupName())
                    .positionId(position.getId())
                    .positionName(position.getPositionName())
                    .userId(userId)
                    .build());
        });
        //查询职位对应角色
        List<UserRole> userRoleList = new ArrayList<>();
        userPositionList.forEach(userPosition -> {
            //查询职位对应角色
            List<PositionRole> positionRoles = positionRoleInnerService.list(positionRoleInnerService.lambdaQuery()
                    .eq(PositionRole::getPositionId, userPosition.getPositionId())
                    .eq(PositionRole::getGroupId, userPosition.getGroupId()));
            if (CollectionUtil.isNotEmpty(positionRoles)) {
                positionRoles.forEach(positionRole -> {
                    userRoleList.add(UserRole.builder()
                            .userId(userId)
                            .roleId(positionRole.getRoleId())
                            .roleName(positionRole.getRoleName())
                            .groupId(positionRole.getGroupId())
                            .groupName(positionRole.getGroupName())
                            .build());
                });
            }
        });
        List<UserPermission> userPermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            //查询权限
            userRoleList.forEach(userRole -> {
                List<RolePermission> rolePermissionList = rolePermissionInnerService.list(rolePermissionInnerService.lambdaQuery()
                        .eq(RolePermission::getRoleId,userRole.getRoleId())
                        .eq(RolePermission::getGroupId,userRole.getGroupId()));
                if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                    rolePermissionList.forEach(rolePermission -> {
                        userPermissionList.add(UserPermission.builder()
                                .userId(userId)
                                .permissionId(rolePermission.getPermissionId())
                                .permissionName(rolePermission.getPermissionName())
                                .groupId(rolePermission.getGroupId())
                                .groupName(rolePermission.getGroupName())
                                .build());
                    });
                }
            });
        }
        try {
            userPositionServiceTransaction.grantNewPositionsToUser(userPositionList,userRoleList,userPermissionList);
            //发送更新事件
            eventPublisher.publishEvent(UserRolePermissionUpdateEvent.builder()
                    .userId(userId)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("事务授权用户职位失败！",e);
            return false;
        }
    }

    public boolean removePositionsFromUser(String userId,List<UserPositionGroupDto> positionGroupDtoList) {
        //检查用户
        Users queryUser = usersInnerService.getById(userId);
        if (null != queryUser && StatusEnums.UN_VALID.getCode() != queryUser.getLoginFlag()) {
            throw new CommonException("用户被封禁，无法授权");
        }
        //查询用户职位
        List<UserPosition> userPositionList = userPositionInnerService.list(userPositionInnerService.lambdaQuery()
                .eq(UserPosition::getUserId,userId)
                .in(UserPosition::getGroupId,positionGroupDtoList.stream().map(UserPositionGroupDto::getGroupId).collect(Collectors.toList()))
                .in(UserPosition::getPositionId,positionGroupDtoList.stream().map(UserPositionGroupDto::getPositionId).collect(Collectors.toList())));
        //查询职位对应角色
        List<PositionRole> positionRoleList = new ArrayList<>();
        List<RolePermission> rolePermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(userPositionList)) {
            positionRoleList = positionRoleInnerService.list(positionRoleInnerService.lambdaQuery()
                    .in(PositionRole::getGroupId,userPositionList.stream().map(UserPosition::getGroupId).collect(Collectors.toList()))
                    .in(PositionRole::getPositionId,userPositionList.stream().map(UserPosition::getPositionId).collect(Collectors.toList())));
            //查询角色对应权限
            if (CollectionUtil.isNotEmpty(positionRoleList)) {
                rolePermissionList = rolePermissionInnerService.list(rolePermissionInnerService.lambdaQuery()
                        .in(RolePermission::getRoleId,positionRoleList.stream().map(PositionRole::getRoleId).collect(Collectors.toList()))
                        .in(RolePermission::getGroupId,positionRoleList.stream().map(PositionRole::getGroupId).collect(Collectors.toList())));
            }
        }
        //查询用户角色
        List<UserRole> userRoleList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(positionRoleList)) {
            userRoleList = userRoleInnerService.list(userRoleInnerService.lambdaQuery()
                    .eq(UserRole::getUserId,userId)
                    .in(UserRole::getGroupId,positionRoleList.stream().map(PositionRole::getGroupId).collect(Collectors.toList()))
                    .in(UserRole::getRoleId,positionRoleList.stream().map(PositionRole::getPositionId).collect(Collectors.toList())));
        }
        //查询用户权限
        List<UserPermission> userPermissionList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            userPermissionList = userPermissionInnerService.list(userPermissionInnerService.lambdaQuery()
                    .eq(UserPermission::getUserId,userId)
                    .in(UserPermission::getGroupId,rolePermissionList.stream().map(RolePermission::getGroupId).collect(Collectors.toList()))
                    .in(UserPermission::getPermissionId,rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList())));
        }
        try {
            userPositionServiceTransaction.removePositionsFromUser(userPositionList, userRoleList, userPermissionList);
            //发送更新事件
            eventPublisher.publishEvent(UserRolePermissionUpdateEvent.builder()
                    .userId(userId)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("事务删除用户职位失败！",e);
            return false;
        }


    }
}
