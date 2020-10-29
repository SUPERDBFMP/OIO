package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPositionGroupInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPositionRoleInnerService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  职位更新事件
 * </p>
 *
 * @author dbfmp
 * @name: PositionUpdateEventListener
 * @since 2020/10/24 9:36 下午
 */
@Slf4j
@Component
public class PositionUpdateEventListener implements RecoveryEvent {

    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;

    @Async("updateMiddleTablePool")
    @EventListener
    public void positionUpdateEvent(PositionUpdateEvent updateEvent) {
        log.info("收到position更新事件,positionUpdateEvent:{}",updateEvent);
        if (StrUtil.isBlank(updateEvent.getId())) {
            log.warn("positionUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
        RLock lock = redissonClient.getLock(updateEvent.getEventId());
        boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            Event event = new Event();
            if (lockFlag) {
                event.setEventStatus(EventStatus.PROCESSING.name());
                eventInnerService.updateById(event);
                PositionGroup positionGroup = PositionGroup.builder()
                        .positionId(updateEvent.getId())
                        .positionName(updateEvent.getPositionName())
                        .build();
                positionGroupInnerService.update(positionGroup,new LambdaQueryWrapper<PositionGroup>().eq(PositionGroup::getPositionId,updateEvent.getId()));
                PositionRole positionRole = PositionRole.builder()
                        .positionId(updateEvent.getId())
                        .positionName(updateEvent.getPositionName())
                        .build();
                positionRoleInnerService.update(positionRole,new LambdaQueryWrapper<PositionRole>().eq(PositionRole::getPositionId,updateEvent.getId()));
                event.setEventStatus(EventStatus.SUCCESS.name());
                event.setRemarks("更新成功！");
                eventInnerService.updateById(event);
                log.info("positionUpdateEvent,更新结束");
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
        PositionUpdateEvent event = JSONObject.parseObject(eventJsonParams,PositionUpdateEvent.class);
        this.positionUpdateEvent(event);
    }
}
