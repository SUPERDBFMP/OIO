package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  角色更新监听
 * </p>
 *
 * @author dbfmp
 * @name: RolesUpdateEventListener
 * @since 2020/10/24 10:13 下午
 */
@Slf4j
@Component
public class RolesUpdateEventListener {

    @Autowired
    private IGroupRoleInnerService groupRoleInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUrlRoleInnerService urlRoleInnerService;
    @Autowired
    private IUserRoleInnerService userRoleInnerService;

    @Async("updateMiddleTablePool")
    @EventListener
    public void roleUpdateEvent(RolesUpdateEvent updateEvent) {
        log.info("收到role更新事件,roleUpdateEvent:{}",updateEvent);
        if (StrUtil.isBlank(updateEvent.getId())) {
            log.warn("roleUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        GroupRole groupRole = GroupRole.builder().roleId(updateEvent.getId()).roleName(updateEvent.getRoleName()).build();
        groupRoleInnerService.update(groupRole,new LambdaQueryWrapper<GroupRole>().eq(GroupRole::getRoleId,updateEvent.getId()));
        PositionRole positionRole = PositionRole.builder().roleId(updateEvent.getId()).roleName(updateEvent.getRoleName()).build();
        positionRoleInnerService.update(positionRole,new LambdaQueryWrapper<PositionRole>().eq(PositionRole::getRoleId,updateEvent.getId()));
        RolePermission rolePermission = RolePermission.builder().roleId(updateEvent.getId()).roleName(updateEvent.getRoleName()).build();
        rolePermissionInnerService.update(rolePermission,new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getRoleId,updateEvent.getId()));
        UrlRole urlRole = UrlRole.builder().roleId(updateEvent.getId()).roleName(updateEvent.getRoleName()).build();
        urlRoleInnerService.update(urlRole,new LambdaQueryWrapper<UrlRole>().eq(UrlRole::getRoleId,updateEvent.getId()));
        UserRole userRole = UserRole.builder().roleId(updateEvent.getId()).roleName(updateEvent.getRoleName()).build();
        userRoleInnerService.update(userRole,new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId,updateEvent.getId()));
        log.info("roleUpdateEvent,更新结束");
    }

}
