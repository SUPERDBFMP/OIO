package indi.dbfmp.oio.oauth.core.innerService.impl;

import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.mapper.EventMapper;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 事件表 服务实现类
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-29
 */
@Service
public class EventInnerServiceImpl extends ServiceImpl<EventMapper, Event> implements IEventInnerService {

}
