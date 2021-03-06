package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.Event;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import indi.dbfmp.oio.oauth.core.entity.Permission;
import indi.dbfmp.oio.oauth.core.enums.EventStatus;
import indi.dbfmp.oio.oauth.core.event.RecoveryEvent;
import indi.dbfmp.oio.oauth.core.innerService.IEventInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IGroupsInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPermissionInnerService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 组织机构更新事件
 * </p>
 *
 * @author dbfmp
 * @name: OrgUpdateEventListner
 * @since 2020/10/24 8:30 下午
 */
@Slf4j
@Component
public class OrgUpdateEventListener implements RecoveryEvent<OrgUpdateEvent> {

    @Autowired
    private IPermissionInnerService permissionInnerService;
    @Autowired
    private IGroupsInnerService groupsInnerService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IEventInnerService eventInnerService;
    //todo 更多表更新


    @Override
    public void eventAction(OrgUpdateEvent orgUpdateEvent) {
        log.info("收到org更新事件,orgUpdateEvent:{}", orgUpdateEvent);
        Event event = new Event();
        event.setId(orgUpdateEvent.getEventId());
        event.setEventStatus(EventStatus.PROCESSING.name());
        eventInnerService.updateById(event);
        Permission permission = Permission.builder().orgId(orgUpdateEvent.getId()).orgName(orgUpdateEvent.getOrgName()).build();
        permissionInnerService.update(permission, new LambdaQueryWrapper<Permission>().eq(Permission::getOrgId, orgUpdateEvent.getId()));
        Groups groups = Groups.builder().orgId(orgUpdateEvent.getId()).orgName(orgUpdateEvent.getOrgName()).build();
        groupsInnerService.update(groups, new LambdaQueryWrapper<Groups>().eq(Groups::getOrgId, orgUpdateEvent.getId()));
        event.setEventStatus(EventStatus.SUCCESS.name());
        event.setRemarks("更新成功！");
        eventInnerService.updateById(event);
        log.info("orgUpdateEvent,更新结束");
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
                OrgUpdateEvent event = JSONObject.parseObject(eventJsonParams, OrgUpdateEvent.class);
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
}
