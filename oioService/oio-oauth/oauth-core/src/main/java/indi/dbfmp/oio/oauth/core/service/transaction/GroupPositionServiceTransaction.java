package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserPosition;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IPositionGroupInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPositionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  分组职位服务使用的事务
 * </p>
 *
 * @author dbfmp
 * @name: GroupPositionServiceTransaction
 * @since 2020/10/26 10:22 下午
 */
@Slf4j
@Service
public class GroupPositionServiceTransaction {

    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IUserPositionInnerService userPositionInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;


    /**
     * 事务移除当前分组下的职位
     * @param groupId 分组id
     * @param positionIdList 职位id列表
     * @param roleIdList 职位对应的角色id列表
     * @param permissionIdList 角色对应的权限id列表
     * @param defaultGroupId 默认分组id
     * @param defaultGroupName 默认分组名
     */
    @Transactional(rollbackFor = Exception.class)
    public void removePositionsFromGroup(String groupId, List<String> positionIdList, List<String> roleIdList,List<String> permissionIdList,String defaultGroupId,String defaultGroupName) {
        positionGroupInnerService.remove(new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId,groupId).in(PositionGroup::getPositionId,positionIdList));
        userPositionInnerService.update(UserPosition.builder()
                .groupId(defaultGroupId)
                .groupName(defaultGroupName)
                .build(),new LambdaQueryWrapper<UserPosition>().eq(UserPosition::getGroupId,groupId));
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            userRoleInnerService.update(UserRole.builder()
                    .groupId(defaultGroupId)
                    .groupName(defaultGroupName)
                    .build(),new LambdaQueryWrapper<UserRole>().eq(UserRole::getGroupId,groupId).in(UserRole::getRoleId,roleIdList));
        }
        if (CollectionUtil.isNotEmpty(permissionIdList)) {
            userPermissionInnerService.update(UserPermission.builder()
                    .groupId(defaultGroupId)
                    .groupName(defaultGroupName)
                    .build(),new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getGroupId,groupId).in(UserPermission::getPermissionId,permissionIdList));
        }
    }

}