package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.dto.GrantAuthDto;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.exception.CommonException;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.GrantUserAuthServiceTransaction;
import indi.dbfmp.validator.core.annotation.ValidateBefore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  授权 服务
 * </p>
 *
 * @author dbfmp
 * @name: GrantAuthService
 * @since 2020/10/20 8:40 下午
 */
@Slf4j
@Service
public class GrantAuthService {

    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IPositionInnerService positionInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private IRolesInnerService rolesInnerService;
    @Autowired
    private IPermissionInnerService permissionInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private GrantUserAuthServiceTransaction grantUserAuthServiceTransaction;

    /**
     * 用户授权
     * @param authDto 授权数据
     */
    @ValidateBefore
    public void grantAuthToUser(GrantAuthDto authDto) {
        /*检查权限合法性*/
        //检查orgId，groupId
        if (orgInnerService.count(new LambdaQueryWrapper<Org>().eq(Org::getId, authDto.getOrgId())) <= 0) {
            log.error("grantAuthToUser,组织机构无效->OrgId:{}",authDto.getOrgId());
            throw new CommonException("组织机构无效！");
        }
        Groups groups;
        if ((groups = groupsInnerService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getId, authDto.getGroupId()).eq(Groups::getOrgId, authDto.getOrgId()))) == null) {
            log.error("grantAuthToUser,分组无效->GroupId:{},OrgId:{}",authDto.getGroupId(),authDto.getOrgId());
            throw new CommonException("分组无效！");
        }
        //todo 按group-role授权
        for (String positionId : authDto.getPositionIdList()) {
            if (positionGroupInnerService.count(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId, authDto.getGroupId()).eq(PositionGroup::getPositionId, positionId)) <= 0) {
                log.error("grantAuthToUser,职位无效->GroupId:{},PositionId:{}",authDto.getGroupId(),positionId);
                throw new CommonException("存在无效职位！");
            }
        }
        //检查角色有效性
        if (CollectionUtil.isEmpty(authDto.getRoleIdList())) {
            //为空直接授权当前职位下的角色与权限
            //查询角色
            List<PositionRole> positionRoleList = positionRoleInnerService.list(new LambdaQueryWrapper<PositionRole>()
                    .in(PositionRole::getPositionId, authDto.getPositionIdList())
                    .eq(PositionRole::getGroupId, authDto.getGroupId()));
            if (CollectionUtil.isNotEmpty(positionRoleList)) {
                List<Position> positionList = positionInnerService.list(new LambdaQueryWrapper<Position>().in(Position::getId, authDto.getPositionIdList()));
                //查询角色
                List<Roles> roleList = rolesInnerService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId, positionRoleList.stream()
                        .map(PositionRole::getRoleId)
                        .collect(Collectors.toList())));
                if (CollectionUtil.isNotEmpty(roleList)) {
                    //查询权限
                    List<RolePermission> rolePermissionList = rolePermissionInnerService.list(new LambdaQueryWrapper<RolePermission>()
                            .in(RolePermission::getRoleId, roleList.stream()
                                    .map(Roles::getId)
                                    .collect(Collectors.toList()))
                            .eq(RolePermission::getGroupId, authDto.getGroupId()));
                    if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                        List<Permission> permissionList = permissionInnerService.list(new LambdaQueryWrapper<Permission>()
                                .in(Permission::getId, rolePermissionList.stream()
                                        .map(RolePermission::getPermissionId)
                                        .collect(Collectors.toList())));
                        //保存用户角色与权限
                        grantUserAuthServiceTransaction.saveUserAuth(authDto.getUserId(), authDto.getGroupId(), groups.getGroupName(), positionList, roleList, permissionList);
                    }

                } else {
                    //保存角色
                    grantUserAuthServiceTransaction.saveUserAuth(authDto.getUserId(), authDto.getGroupId(), groups.getGroupName(), positionList, roleList, null);
                }
            }
        } else {
            //保存传入的角色
            //检查角色是否属于当前职位
            for (String roleId : authDto.getRoleIdList()) {
                PositionRole positionRole = positionRoleInnerService.getOne(new LambdaQueryWrapper<PositionRole>()
                        .eq(PositionRole::getGroupId,authDto.getGroupId())
                        .eq(PositionRole::getRoleId,roleId)
                        .in(PositionRole::getPositionId,authDto.getPositionIdList()));
                if (null == positionRole) {
                    log.error("存在无效角色授权,userId:{},groupId:{},roleId:{},positionIdList:{}",authDto.getUserId(),authDto.getGroupId(),roleId,authDto.getPositionIdList());
                    throw new CommonException("存在无效角色！");
                }
            }
            //检查通过，查询职位
            List<Position> positionList = positionInnerService.list(new LambdaQueryWrapper<Position>().in(Position::getId, authDto.getPositionIdList()));
            //查询角色
            List<Roles> roleList = rolesInnerService.list(new LambdaQueryWrapper<Roles>().in(Roles::getId,authDto.getRoleIdList()));
            if (CollectionUtil.isNotEmpty(roleList)) {
                //查询权限
                List<RolePermission> rolePermissionList = rolePermissionInnerService.list(new LambdaQueryWrapper<RolePermission>()
                        .in(RolePermission::getRoleId, roleList.stream()
                                .map(Roles::getId)
                                .collect(Collectors.toList()))
                        .eq(RolePermission::getGroupId, authDto.getGroupId()));
                if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                    List<Permission> permissionList = permissionInnerService.list(new LambdaQueryWrapper<Permission>()
                            .in(Permission::getId, rolePermissionList.stream()
                                    .map(RolePermission::getPermissionId)
                                    .collect(Collectors.toList())));
                    //保存用户角色与权限
                    grantUserAuthServiceTransaction.saveUserAuth(authDto.getUserId(), authDto.getGroupId(), groups.getGroupName(), positionList, roleList, permissionList);
                }

            } else {
                //保存角色
                grantUserAuthServiceTransaction.saveUserAuth(authDto.getUserId(), authDto.getGroupId(), groups.getGroupName(), positionList, roleList, null);
            }
        }
    }

}
