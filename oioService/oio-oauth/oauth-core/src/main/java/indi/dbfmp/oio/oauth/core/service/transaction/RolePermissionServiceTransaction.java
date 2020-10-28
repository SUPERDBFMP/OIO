package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.RolePermission;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.innerService.IRolePermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  角色权限事务服务
 * </p>
 *
 * @author dbfmp
 * @name: RolePermissionTransaction
 * @since 2020/10/28 10:28 下午
 */
@Slf4j
@Service
public class RolePermissionServiceTransaction {

    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    @Transactional(rollbackFor = Exception.class)
    public void addNewPermissionToRole(List<RolePermission> rolePermissions, List<UserPermission> userPermissions) {
        rolePermissionInnerService.saveBatch(rolePermissions);
        if (CollectionUtil.isNotEmpty(userPermissions)) {
            userPermissionInnerService.saveBatch(userPermissions);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removePermissionFromRole(String groupId,String roleId,List<String> permissionIdList) {
        rolePermissionInnerService.remove(new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getGroupId,groupId).eq(RolePermission::getRoleId,roleId).in(RolePermission::getPermissionId,permissionIdList));
        userPermissionInnerService.remove(new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getGroupId,groupId).eq(UserPermission::getPermissionId,permissionIdList));
    }

}
