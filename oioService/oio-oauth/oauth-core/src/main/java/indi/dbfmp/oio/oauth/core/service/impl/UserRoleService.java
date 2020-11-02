package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.event.update.UserRolePermissionUpdateEvent;
import indi.dbfmp.oio.oauth.core.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.UserRoleServiceTransaction;
import indi.dbfmp.web.common.dto.CommonResult;
import inid.dbfmp.oauth.api.enums.StatusEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  用户角色服务
 * </p>
 *
 * @author dbfmp
 * @name: UserRoleService
 * @since 2020/11/2 9:31 下午
 */
@Slf4j
@Service
public class UserRoleService {

    @Autowired
    private IUsersInnerService usersInnerService;
    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private UserRoleServiceTransaction userRoleServiceTransaction;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 授权新角色给用户
     *
     * 默认分组名${orgCode}:default
     *
     * @param userId 用户id
     * @param roleIdList 角色id列表
     * @return 是否授权成功
     */
    public boolean grantNewRolesToUser(String userId, List<String> roleIdList) {
        //检查用户
        Users queryUser = usersInnerService.getById(userId);
        if (null != queryUser && StatusEnums.UN_VALID.getCode() != queryUser.getLoginFlag()) {
            throw new CommonException("用户被封禁，无法授权");
        }
        //查询角色
        List<Roles> rolesList = rolesInnerService.listByIds(roleIdList);
        if (CollectionUtil.isEmpty(rolesList)) {
            return true;
        }
        //组装用户角色,分组设置为role对应org下默认分组
        List<UserRole> userRoleList = new ArrayList<>(rolesList.size());
        rolesList.forEach(roles -> {
            Org queryOrg = orgInnerService.getById(roles.getOrgId());
            //查询默认分组ID
            Groups defaultGroup = groupsInnerService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getOrgId,queryOrg.getId()).eq(Groups::getGroupCode,queryOrg.getOrgCode() + ":default"));
            userRoleList.add(UserRole.builder()
                    .roleName(roles.getRoleName())
                    .roleId(roles.getId())
                    .userId(userId)
                    .groupId(defaultGroup.getId())
                    .groupName(defaultGroup.getGroupName())
                    .build());
        });
        //授予权限
        List<UserPermission> userPermissionList = new ArrayList<>();
        userRoleList.forEach(userRole -> {
            List<RolePermission> rolePermissionList = rolePermissionInnerService.lambdaQuery()
                    .eq(RolePermission::getRoleId, userRole.getRoleId())
                    .eq(RolePermission::getGroupId, userRole.getGroupId())
                    .list();
            if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                rolePermissionList.forEach(rolePermission -> {
                    userPermissionList.add(UserPermission.builder()
                            .userId(userId)
                            .permissionId(rolePermission.getId())
                            .permissionName(rolePermission.getPermissionName())
                            .groupId(userRole.getGroupId())
                            .groupName(userRole.getGroupName())
                            .build());
                });
            }
        });
        try {
            userRoleServiceTransaction.grantNewRolesToUser(userRoleList, userPermissionList);
            //todo 发送更新事件
            eventPublisher.publishEvent(UserRolePermissionUpdateEvent.builder()
                    .userId(userId)
                    .build());
            return true;
        } catch (Exception e) {
            log.error("事务授权用户角色失败！",e);
            return false;
        }
    }


}
