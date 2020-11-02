package indi.dbfmp.oio.oauth.core.service.cron;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通知补偿调度
 * </p>
 *
 * @author dbfmp
 * @name: EventCompensationScheduleService
 * @since 2020/10/29 8:55 下午
 */
@Slf4j
@Service
public class EventCompensationScheduleService {

    @Autowired
    private Map<String, RecoveryEvent> recoveryEventMap;
    @Autowired
    private IEventInnerService eventInnerService;


    @Async("updateMiddleTablePool")
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void eventCompensation() {
        //查询10分钟之前更新，且是未终态的数据
        List<Event> eventList = eventInnerService.list(new LambdaQueryWrapper<Event>()
                .eq(Event::getEventStatus, EventStatus.PROCESSING.name())
                .le(Event::getUpdateDate, DateUtil.offsetMinute(new Date(),-10))
                .orderByAsc(Event::getCreateDate));
        for (Event event : eventList) {
            RecoveryEvent recoveryEvent = getRecoveryEvent(event.getEventBeanName());
            if (null == recoveryEvent) {
                event.setRemarks("未找到事件处理bean，beanName:" + event.getEventBeanName());
                event.setEventStatus(EventStatus.FAIL.name());
                eventInnerService.updateById(event);
                continue;
            }
           recoveryEvent.recoveryEventAction(event.getId(), event.getEventParams());
        }
    }

    private RecoveryEvent getRecoveryEvent(String className) {
        if (recoveryEventMap.containsKey(StrUtil.lowerFirst(className))) {
            return recoveryEventMap.get(StrUtil.lowerFirst(className));
        }
        return null;
    }

}
