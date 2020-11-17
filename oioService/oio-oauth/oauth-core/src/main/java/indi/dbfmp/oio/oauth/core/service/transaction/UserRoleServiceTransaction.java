package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import indi.dbfmp.oio.oauth.core.dto.UserRoleGroupDto;
import indi.dbfmp.oio.oauth.core.entity.RolePermission;
import indi.dbfmp.oio.oauth.core.entity.Roles;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  用户权限事务服务
 * </p>
 *
 * @author dbfmp
 * @name: UserRoleTransactionService
 * @since 2020/11/2 10:09 下午
 */
@Slf4j
@Service
public class UserRoleServiceTransaction {

    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void grantNewRolesToUser(List<UserRole> userRoleList, List<UserPermission> userPermissionList) {
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.saveOrUpdateBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            userPermissionInnerService.saveOrUpdateBatch(userPermissionList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeRolesFromUser(String userId,List<Roles> rolesList, List<RolePermission> rolePermissionList) {
        if (CollectionUtil.isNotEmpty(rolesList)) {
            userRoleInnerService.remove(userRoleInnerService.lambdaQuery()
                    .eq(UserRole::getUserId,userId)
                    .in(UserRole::getRoleId,rolesList.stream().map(Roles::getId).collect(Collectors.toList())));
        }
        if (CollectionUtil.isNotEmpty(rolePermissionList)) {
            userPermissionInnerService.remove(userPermissionInnerService.lambdaQuery()
                    .eq(UserPermission::getUserId,userId)
                    .in(UserPermission::getPermissionId,rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList())));
        }
    }

}
