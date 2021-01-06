package indi.dbfmp.oio.oauth.core.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.enums.EventTypes;
import indi.dbfmp.oio.oauth.core.event.BaseEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  事件服务类
 * </p>
 *
 * @author dbfmp
 * @name: EventService
 * @since 2020/11/23 下午10:59
 */
@Slf4j
@Service
public class EventService {

    @Autowired
    private IEventInnerService eventInnerService;

    /**
     * 创建处理中的事件
     * @param eventBeanName 事件beanName
     * @param eventParams 事件参数
     * @param eventTypes 事件类型
     * @return 事件对象
     */
    public Event createProcessingEvent(String eventBeanName, BaseEvent eventParams, EventTypes eventTypes) {
        String id = IdUtil.simpleUUID();
        eventParams.setEventId(id);
        Event event = Event.builder()
                .id(id)
                .eventBeanName(eventBeanName)
                .eventParams(JSONObject.toJSONString(eventParams))
                .eventStatus(EventStatus.PROCESSING.name())
                .eventType(eventTypes.name())
                .build();
        eventInnerService.save(event);
        return event;
    }

}
