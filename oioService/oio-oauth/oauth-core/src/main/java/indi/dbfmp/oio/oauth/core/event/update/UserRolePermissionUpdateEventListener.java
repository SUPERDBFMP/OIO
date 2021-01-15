package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import indi.dbfmp.oio.oauth.core.component.RedisUtil;
import indi.dbfmp.oio.oauth.core.constants.OauthRedisConstants;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  用户角色权限更新监听器
 * </p>
 *
 * @author dbfmp
 * @name: UserRolePermissionUpdateEventListener
 * @since 2020/11/2 10:20 下午
 */
@Slf4j
@Component
public class UserRolePermissionUpdateEventListener implements RecoveryEvent<UserRolePermissionUpdateEvent> {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 恢复通知操作
     * <p>
     * 把json格式的event事件转换为实际参数，进行调用
     *
     * @param eventId 事件ID
     * @param eventJsonParams eventJsonParams
     */
    @Override
    public void recoveryEventAction(String eventId, String eventJsonParams) {
        RLock lock = redissonClient.getLock(eventId);
        boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            if (lockFlag) {
                UserRolePermissionUpdateEvent event = JSONObject.parseObject(eventJsonParams, UserRolePermissionUpdateEvent.class);
                this.eventAction(event);
            }else {
                log.warn("获取不到事件ID锁:{},退出！",eventId);
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

    @Override
    public void eventAction(UserRolePermissionUpdateEvent updateEvent) {
        Event event = new Event();
        event.setId(updateEvent.getEventId());
        event.setEventStatus(EventStatus.PROCESSING.name());
        eventInnerService.updateById(event);
        List<String> matchKeyList = new ArrayList<>();
        RSet<String> userCacheIdSet = redissonClient.getSet(updateEvent.getUserId());
        //查询缓存id
        String roleMatchKey = StrUtil.format(OauthRedisConstants.URL_ROLE_CHECK_ANT_MATCH,updateEvent.getUserId());
        String perMissionMatchKey = StrUtil.format(OauthRedisConstants.URL_PERMISSION_ANT_MATCH,updateEvent.getUserId());
        PathMatcher pathMatcher = new AntPathMatcher();
        userCacheIdSet.forEach(value->{
            if (pathMatcher.match(roleMatchKey, value) || pathMatcher.match(perMissionMatchKey, value)) {
                matchKeyList.add(value);
            }
        });
        redisUtil.del(matchKeyList);
        event.setEventStatus(EventStatus.SUCCESS.name());
        event.setRemarks("更新成功！");
        eventInnerService.updateById(event);
        log.info("删除用户:{}鉴权缓存成功！",updateEvent.getUserId());
    }
}
