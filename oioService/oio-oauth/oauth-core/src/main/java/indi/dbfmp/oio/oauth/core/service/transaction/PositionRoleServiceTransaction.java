package indi.dbfmp.oio.oauth.core.service.transaction;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import indi.dbfmp.oio.oauth.core.innerService.IPositionRoleInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  职位角色事务服务
 * </p>
 *
 * @author dbfmp
 * @name: PositionRoleServiceTransaction
 * @since 2020/10/27 10:56 下午
 */
@Slf4j
@Service
public class PositionRoleServiceTransaction {

    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;

    /**
     * 添加角色到职位中
     *
     * @param positionRoleList     职位角色对象列表
     * @param userRoleList        用户角色数据列表
     * @param userPermissionsList 用户权限数据列表
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRolesToPosition(List<PositionRole> positionRoleList, List<UserRole> userRoleList, List<UserPermission> userPermissionsList) {
        if (CollectionUtil.isNotEmpty(positionRoleList)) {
          positionRoleInnerService.saveBatch(positionRoleList);
        }
        if (CollectionUtil.isNotEmpty(userRoleList)) {
            userRoleInnerService.saveBatch(userRoleList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionsList)) {
            userPermissionInnerService.saveBatch(userPermissionsList);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeRolesFromPosition(String groupId, String positionId, List<String> roleIdList, List<String> userRoleIdList, List<String> userPermissionIdList) {
        positionRoleInnerService.remove(new LambdaQueryWrapper<PositionRole>().eq(PositionRole::getGroupId,groupId).eq(PositionRole::getPositionId,positionId).in(PositionRole::getRoleId,roleIdList));
        if (CollectionUtil.isNotEmpty(userRoleIdList)) {
            userRoleInnerService.removeByIds(userRoleIdList);
        }
        if (CollectionUtil.isNotEmpty(userPermissionIdList)) {
            userPermissionInnerService.removeByIds(userPermissionIdList);
        }
    }
}
