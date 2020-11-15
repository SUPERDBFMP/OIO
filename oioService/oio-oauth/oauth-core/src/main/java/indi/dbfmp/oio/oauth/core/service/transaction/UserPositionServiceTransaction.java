package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
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
 *
 * </p>
 *
 * @author dbfmp
 * @name: UserPositionServiceTransaction
 * @since 2020/11/9 11:03 下午
 */
@Slf4j
@Service
public class UserPositionServiceTransaction {

    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void grantNewPositionsToUser(List<UserRole> userRoleList,List<UserPermission> userPermissionList) {
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.saveOrUpdateBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            userPermissionInnerService.saveOrUpdateBatch(userPermissionList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removePositionsFromUser(List<UserRole> userRoleList,List<UserPermission> userPermissionList) {
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.removeByIds(userRoleList.stream().map(UserRole::getId).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(userPermissionList)) {
            userPermissionInnerService.removeByIds(userPermissionList.stream().map(UserPermission::getId).collect(Collectors.toList()));
        }
    }



}
