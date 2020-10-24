package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import indi.dbfmp.oio.oauth.core.innerService.IPositionGroupInnerService;
import indi.dbfmp.oio.oauth.core.innerService.IPositionRoleInnerService;
import lombok.extern.slf4j.Slf4j;
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
public class PositionUpdateEventListener {

    @Autowired
    private IPositionGroupInnerService positionGroupInnerService;
    @Autowired
    private IPositionRoleInnerService positionRoleInnerService;

    @Async("updateMiddleTablePool")
    @EventListener
    public void positionUpdateEvent(PositionUpdateEvent updateEvent) {
        log.info("收到position更新事件,positionUpdateEvent:{}",updateEvent);
        if (StrUtil.isBlank(updateEvent.getId())) {
            log.warn("positionUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
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
        log.info("positionUpdateEvent,更新结束");
    }

}
