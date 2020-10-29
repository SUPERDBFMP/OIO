package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
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
public class RolesUpdateEventListener implements RecoveryEvent {

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
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;

    @Async("updateMiddleTablePool")
    @EventListener
    public void roleUpdateEvent(RolesUpdateEvent updateEvent) {
        log.info("收到role更新事件,roleUpdateEvent:{}",updateEvent);
        if (StrUtil.isBlank(updateEvent.getId())) {
            log.warn("roleUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        RLock lock = redissonClient.getLock(updateEvent.getEventId());
        boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            Event event = new Event();
            if (lockFlag) {
                event.setRemarks("补偿处理中");
                event.setEventStatus(EventStatus.PROCESSING.name());
                eventInnerService.updateById(event);
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
                event.setEventStatus(EventStatus.SUCCESS.name());
                event.setRemarks("更新成功！");
                eventInnerService.updateById(event);
                log.info("roleUpdateEvent,更新结束");
            }
        } catch (Exception e) {
            log.error("锁内执行事件失败！",e);
            log.error("锁内执行事件失败！event:{}",updateEvent.getEventId());
        }finally {
            if (lockFlag) {
                lock.unlock();
            }
        }
    }

    /**
     * 恢复通知操作
     * <p>
     * 把json格式的event事件转换为实际参数，进行调用
     *
     * @param eventJsonParams eventJsonParams
     */
    @Override
    public void recoveryEventAction(String eventJsonParams) {
        RolesUpdateEvent event = JSONObject.parseObject(eventJsonParams,RolesUpdateEvent.class);
        this.roleUpdateEvent(event);
    }
}
