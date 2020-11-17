package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  分组角色事务服务
 * </p>
 *
 * @author dbfmp
 * @name GroupRoleServiceTransaction
 * @since 2020/10/27 10:16 下午
 */
@Slf4j
@Service
public class GroupRoleServiceTransaction {

    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    /**
     * 事务移除当前分组下的角色
     * @param groupId 分组id
     * @param roleIdList 分组对应的角色id列表
     * @param permissionIdList 角色对应的权限id列表
     * @param defaultGroupId 默认分组id
     * @param defaultGroupName 默认分组名
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeRolesFromGroup(String groupId, List<String> roleIdList, List<String> permissionIdList, String defaultGroupId, String defaultGroupName) {
        /*userRoleInnerService.update(UserRole.builder()
                .groupId(defaultGroupId)
                .groupName(defaultGroupName)
                .build(),new LambdaQueryWrapper<UserRole>().eq(UserRole::getGroupId,groupId).in(UserRole::getRoleId,roleIdList));
        if (CollectionUtil.isNotEmpty(permissionIdList)) {
            userPermissionInnerService.update(UserPermission.builder()
                    .groupId(defaultGroupId)
                    .groupName(defaultGroupName)
                    .build(),new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getGroupId,groupId).in(UserPermission::getPermissionId,permissionIdList));
        }*/
    }

}
