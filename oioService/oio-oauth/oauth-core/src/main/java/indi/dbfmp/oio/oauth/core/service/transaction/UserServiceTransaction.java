package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import indi.dbfmp.oio.oauth.core.entity.GroupUser;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.entity.Users;
import indi.dbfmp.oio.oauth.core.innerService.IGroupUserInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUsersInnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  用户服务事务
 * </p>
 *
 * @author dbfmp
 * @name: UserServiceTransaction
 * @since 2021/3/1 下午9:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceTransaction {


    private final IUserRoleInnerService userRoleInnerService;

    private final IUserPermissionInnerService userPermissionInnerService;

    private final IGroupUserInnerService groupUserInnerService;

    private final IUsersInnerService usersInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void delUser(String delUserId) {
        userPermissionInnerService.remove(Wrappers.lambdaQuery(UserPermission.class).eq(UserPermission::getUserId,delUserId));
        userRoleInnerService.remove(Wrappers.lambdaQuery(UserRole.class).eq(UserRole::getUserId,delUserId));
        groupUserInnerService.remove(Wrappers.lambdaQuery(GroupUser.class).eq(GroupUser::getUserId,delUserId));
        usersInnerService.remove(Wrappers.lambdaQuery(Users.class).eq(Users::getId,delUserId));
    }

}
