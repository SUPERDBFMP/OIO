package indi.dbfmp.oio.oauth.core.service.transaction;

import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  保存用户权限，事务服务
 * </p>
 *
 * @author dbfmp
 * @name: GrantUserAuthServiceTransaction
 * @since 2020/10/20 10:19 下午
 */
@Slf4j
@Service
public class GrantUserAuthServiceTransaction {

    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    /*@Transactional(rollbackFor = Exception.class)
    public void saveUserAuth(String userId, String groupId,String groupName,List<Position> positionList, List<Roles> rolesList, List<Permission> permissionList) {
        //组装
        if (CollectionUtil.isNotEmpty(positionList)) {
            List<UserPosition> userPositionList = new ArrayList<>(positionList.size());
            positionList.forEach(position -> userPositionList.add(UserPosition.builder()
                    .positionId(position.getId())
                    .positionName(position.getPositionName())
                    .userId(userId)
                    .groupId(groupId)
                    .groupName(groupName)
                    .build()));
            userPositionInnerService.saveBatch(userPositionList);
        }
        if (CollectionUtil.isNotEmpty(rolesList)) {
            List<UserRole> userRoleList = new ArrayList<>(rolesList.size());
            rolesList.forEach(roles -> userRoleList.add(UserRole.builder()
                    .roleId(roles.getId())
                    .roleName(roles.getRoleName())
                    .userId(userId)
                    .groupId(groupId)
                    .groupName(groupName)
                    .build()));
            userRoleInnerService.saveBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(permissionList)) {
            List<UserPermission> userPermissionList = new ArrayList<>(permissionList.size());
            permissionList.forEach(permission -> userPermissionList.add(UserPermission.builder()
                    .permissionId(permission.getId())
                    .permissionName(permission.getPermissionName())
                    .userId(userId)
                    .groupId(groupId)
                    .groupName(groupName)
                    .build()));
            userPermissionInnerService.saveBatch(userPermissionList);
        }
    }*/

}
