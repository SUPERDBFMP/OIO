package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import indi.dbfmp.oio.oauth.core.service.transaction.GroupPositionServiceTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * <p>
 *  分组角色服务
 * </p>
 *
 * @author dbfmp
 * @name: GroupPositionService
 * @since 2020/10/26 9:39 下午
 */
@Slf4j
@Service
public class GroupPositionService {

    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private IPositionInnerService positionInnerService;
    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IOrgInnerService orgInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private GroupPositionServiceTransaction groupPositionServiceTransaction;

    /**
     * 添加新的职位至分组中
     *
     * 添加完成后，原有通过group授权职位，角色，权限的用户对应的数据不会作出改变
     *
     * @param groupId 分组id
     * @param positionIdList 职位id列表
     * @return 是否添加成功
     */
    public boolean addNewPositionToGroup(String groupId, List<String> positionIdList) {
        log.info("addNewPositionToGroup,groupId:{};positionIdList:{}",groupId,positionIdList);
        //检查groupId 合法性
        Groups queryGroup = groupsInnerService.getById(groupId);
        if (null == queryGroup) {
            log.warn("无效的groupId");
            return false;
        }
        //检查positionId的合法性
        List<Position> positionList = positionInnerService.listByIds(positionIdList);
        if (positionIdList.size() != positionList.size()) {
            log.warn("存在无效的positionId");
        }
        //添加数据
        List<PositionGroup> positionGroupList = new ArrayList<>(positionIdList.size());
        positionList.forEach(position -> {
            //检查是否已经有数据
            if (positionGroupInnerService.count(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId, queryGroup.getId()).eq(PositionGroup::getPositionId, position.getId())) == 0) {
                positionGroupList.add(PositionGroup.builder()
                        .groupId(queryGroup.getId())
                        .groupName(queryGroup.getGroupName())
                        .positionId(position.getId())
                        .positionName(position.getPositionName())
                        .build());
                }
            });
        return positionGroupInnerService.saveBatch(positionGroupList);
    }

    /**
     *
     * 把原有在group中的职位移除
     *
     * 原有通过group授权的用户职位，角色，权限的分组将调整到默认分组中
     *
     * 默认分组名${orgCode}:default
     *
     * @param groupId 分组id
     * @param positionIdList 职位id列表
     * @return 是否移除成功
     */
    public boolean removePositionsFromGroup(String groupId, List<String> positionIdList) {
        log.info("removePositionsFromGroup,groupId:{};positionIdList:{}",groupId,positionIdList);
        //检查groupId 合法性
        Groups queryGroup = groupsInnerService.getById(groupId);
        if (null == queryGroup) {
            log.warn("无效的groupId");
            return false;
        }
        //查询org
        Org queryOrg = orgInnerService.getById(queryGroup.getOrgId());
        if (null == queryOrg) {
            log.warn("无效的组织机构,orgId:{}",queryGroup.getOrgId());
            return false;
        }
        //检查positionIdList合法性
        if (positionGroupInnerService.count(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId, groupId).in(PositionGroup::getPositionId, positionIdList)) != positionIdList.size()) {
            log.warn("存在不是当前分组下的职位");
            return false;
        }
        //查询职位对应的角色
        List<PositionRole> positionRoleList = positionRoleInnerService.list(new LambdaQueryWrapper<PositionRole>().in(PositionRole::getPositionId,positionIdList).eq(PositionRole::getGroupId,groupId));
        List<String> roleIdList = null;
        List<String> permissionIdList = null;
        if (CollectionUtil.isNotEmpty(positionRoleList)) {
            roleIdList = positionRoleList.stream().map(PositionRole::getRoleId).collect(toList());
            List<RolePermission> rolePermissionList = rolePermissionInnerService.list(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getGroupId,groupId).in(RolePermission::getRoleId,roleIdList));
            if (CollectionUtil.isNotEmpty(rolePermissionList)) {
                permissionIdList = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(toList());
            }
        }
        //查询默认分组ID
        Groups defaultGroup = groupsInnerService.getOne(new LambdaQueryWrapper<Groups>().eq(Groups::getOrgId,queryOrg.getId()).eq(Groups::getGroupCode,queryOrg.getOrgCode() + ":default"));
        if (null == defaultGroup) {
            log.error("未找到默认分组，请配置！org:{}",queryOrg);
            return false;
        }
        try {
            groupPositionServiceTransaction.removePositionsFromGroup(groupId, positionIdList, roleIdList, permissionIdList, defaultGroup.getId(), defaultGroup.getGroupName());
            return true;
        } catch (Exception e) {
            log.error("事务removePositionsFromGroup执行失败！",e);
            return false;
        }
    }

}
