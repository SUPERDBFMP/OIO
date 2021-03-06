package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IRolePermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUrlPermissionInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IUserPermissionInnerService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 权限更新监听器
 * </p>
 *
 * @author dbfmp
 * @name: PermissionUpdateEventListener
 * @since 2020/10/24 10:51 下午
 */
@Slf4j
@Component
public class PermissionUpdateEventListener implements RecoveryEvent<PermissionUpdateEvent> {

    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;
    @Autowired
    private IUserPermissionInnerService userPermissionInnerService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;

    @Override
    public void eventAction(PermissionUpdateEvent updateEvent) {
        log.info("收到permission更新事件,permissionUpdateEvent:{}", updateEvent);
        Event event = new Event();
        event.setId(updateEvent.getEventId());
        event.setEventStatus(EventStatus.PROCESSING.name());
        eventInnerService.updateById(event);
        RolePermission rolePermission = RolePermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        rolePermissionInnerService.update(rolePermission, new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getPermissionId, updateEvent.getId()));
        UrlPermission urlPermission = UrlPermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        urlPermissionInnerService.update(urlPermission, new LambdaQueryWrapper<UrlPermission>().eq(UrlPermission::getPermissionId, updateEvent.getId()));
        UserPermission userPermission = UserPermission.builder().permissionId(updateEvent.getId()).permissionName(updateEvent.getPermissionName()).build();
        userPermissionInnerService.update(userPermission, new LambdaQueryWrapper<UserPermission>().eq(UserPermission::getPermissionId, updateEvent.getId()));
        event.setEventStatus(EventStatus.SUCCESS.name());
        event.setRemarks("更新成功！");
        eventInnerService.updateById(event);
        log.info("permissionUpdateEvent,更新结束");
    }

    /**
     * 恢复通知操作
     * <p>
     * 把json格式的event事件转换为实际参数，进行调用
     *
     * @param eventId         事件ID
     * @param eventJsonParams eventJsonParams
     */
    @Override
    public void recoveryEventAction(String eventId, String eventJsonParams) {
        RLock lock = redissonClient.getLock(eventId);
        boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            if (lockFlag) {
                PermissionUpdateEvent event = JSONObject.parseObject(eventJsonParams, PermissionUpdateEvent.class);
                this.eventAction(event);
            } else {
                log.warn("获取不到事件ID锁:{},退出！", eventId);
            }
        } catch (Exception e) {
            log.error("锁内执行事件失败！", e);
            log.error("锁内执行事件失败！event:{}", eventId);
        } finally {
            if (lockFlag) {
                lock.unlock();
            }
        }
    }
}
