package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void grantNewRolesToUser(List<UserRole> userRoleList, List<UserPermission> userPermissionList) {
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.saveOrUpdateBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            userPermissionInnerService.saveOrUpdateBatch(userPermissionList);
        }
    }

}
