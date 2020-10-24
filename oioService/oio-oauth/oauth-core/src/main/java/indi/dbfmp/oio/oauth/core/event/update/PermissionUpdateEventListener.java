package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.IRolePermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUrlPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  权限更新监听器
 * </p>
 *
 * @author dbfmp
 * @name: PermissionUpdateEventListener
 * @since 2020/10/24 10:51 下午
 */
@Slf4j
@Component
public class PermissionUpdateEventListener {

    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;


    @Async("updateMiddleTablePool")
    @EventListener
    public void permissionUpdateEvent(PermissionUpdateEvent updateEvent) {
        log.info("收到permission更新事件,permissionUpdateEvent:{}",updateEvent);
        if (StrUtil.isBlank(updateEvent.getId())) {
            log.warn("permissionUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        RolePermission rolePermission = RolePermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        rolePermissionInnerService.update(rolePermission,new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId,updateEvent.getId()));
        UrlPermission urlPermission = UrlPermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        urlPermissionInnerService.update(urlPermission,new LambdaQueryWrapper<UrlPermission>().eq(UrlPermission::getPermissionId,updateEvent.getId()));
        UserPermission userPermission = UserPermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        userPermissionInnerService.update(userPermission,new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getPermissionId,updateEvent.getId()));
        log.info("permissionUpdateEvent,更新结束");
    }

}
