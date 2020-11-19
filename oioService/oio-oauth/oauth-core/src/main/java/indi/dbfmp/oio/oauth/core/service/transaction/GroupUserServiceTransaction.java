package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import indi.dbfmp.oio.oauth.core.entity.GroupUser;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IGroupUserInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GroupUserServiceTransaction
 * @since 2020/11/19 下午9:50
 */
@Slf4j
@Service
public class GroupUserServiceTransaction {


    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private IGroupUserInnerService groupUserInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void saveUserToNewGroup(GroupUser groupUser,List<UserRole> userRoleList,List<UserPermission> userPermissionList) {
        groupUserInnerService.save(groupUser);
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.saveBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            userPermissionInnerService.saveBatch(userPermissionList);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void removeUserFromGroup(String userId, String groupId, List<String> roleIdList, List<String> permissionIdList) {
        groupUserInnerService.remove(groupUserInnerService.lambdaQuery().eq(GroupUser::getUserId,userId).eq(GroupUser::getGroupId,groupId));
        if (CollectionUtil.isNotEmpty(roleIdList)) {
            userRoleInnerService.remove(userRoleInnerService.lambdaQuery().eq(UserRole::getUserId,userId).in(UserRole::getRoleId,roleIdList));
        }
        if (CollectionUtil.isNotEmpty(permissionIdList)) {
            userPermissionInnerService.remove(userPermissionInnerService.lambdaQuery().eq(UserPermission::getUserId,userId).in(UserPermission::getPermissionId,permissionIdList));
        }
    }

}
