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
 *  分组更新监听器
 * </p>
 *
 * @author dbfmp
 * @name: GroupsUpdateEventListener
 * @since 2020/10/24 9:07 下午
 */
@Slf4j
@Component
public class GroupsUpdateEventListener implements RecoveryEvent<GroupsUpdateEvent> {

    @Autowired
    private IGroupRoleInnerService groupRoleInnerService;
    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IRolePermissionInnerService rolePermissionInnerService;
    @Autowired
    private IUrlPermissionInnerService urlPermissionInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;


    /**
     * 恢复通知操作
     * <p>
     * 把json格式的event事件转换为实际参数，进行调用
     *
     * @param eventJsonParams eventJsonParams
     */
    @Override
    public void recoveryEventAction(String eventJsonParams) {
        GroupsUpdateEvent event = JSONObject.parseObject(eventJsonParams,GroupsUpdateEvent.class);
        this.eventAction(event);
    }

    public void eventAction(GroupsUpdateEvent groupsUpdateEvent) {
        log.info("收到group更新事件,groupsUpdateEvent:{}",groupsUpdateEvent);
        if (StrUtil.isBlank(groupsUpdateEvent.getId())) {
            log.warn("groupsUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        RLock lock = redissonClient.getLock(groupsUpdateEvent.getEventId());
        boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            Event event = new Event();
            if (lockFlag) {
                event.setEventStatus(EventStatus.PROCESSING.name());
                eventInnerService.updateById(event);
                GroupRole groupRole = GroupRole.builder()
                        .groupId(groupsUpdateEvent.getId())
                        .groupName(groupsUpdateEvent.getGroupName())
                        .build();
                groupRoleInnerService.update(groupRole,new LambdaQueryWrapper<GroupRole>().eq(GroupRole::getGroupId, groupsUpdateEvent.getId()));
                PositionGroup positionGroup = PositionGroup.builder()
                        .groupId(groupsUpdateEvent.getId())
                        .groupName(groupsUpdateEvent.getGroupName())
                        .build();
                positionGroupInnerService.update(positionGroup,new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getGroupId,groupsUpdateEvent.getId()));
                RolePermission rolePermission = RolePermission.builder().groupId(groupsUpdateEvent.getId()).groupName(groupsUpdateEvent.getGroupName()).build();
                rolePermissionInnerService.update(rolePermission,new LambdaQueryWrapper<RolePermission>().eq(RolePermission::getGroupId,groupsUpdateEvent.getId()));
                UrlPermission urlPermission = UrlPermission.builder().groupId(groupsUpdateEvent.getId()).groupName(groupsUpdateEvent.getGroupName()).build();
                urlPermissionInnerService.update(urlPermission,new LambdaQueryWrapper<UrlPermission>().eq(UrlPermission::getGroupId,groupsUpdateEvent.getId()));
                PositionRole positionRole = PositionRole.builder().groupId(groupsUpdateEvent.getId()).groupName(groupsUpdateEvent.getGroupName()).build();
                positionRoleInnerService.update(positionRole,new LambdaQueryWrapper<PositionRole>().eq(PositionRole::getGroupId,groupsUpdateEvent.getId()));
                event.setEventStatus(EventStatus.SUCCESS.name());
                event.setRemarks("更新成功！");
                eventInnerService.updateById(event);
                log.info("groupsUpdateEvent,更新结束");
            }
        } catch (Exception e) {
            log.error("锁内执行事件失败！",e);
            log.error("锁内执行事件失败！event:{}",groupsUpdateEvent.getEventId());
        }finally {
            if (lockFlag) {
                lock.unlock();
            }
        }
    }
}
