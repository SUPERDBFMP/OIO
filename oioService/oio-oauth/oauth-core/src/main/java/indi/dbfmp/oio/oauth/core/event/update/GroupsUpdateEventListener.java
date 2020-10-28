package indi.dbfmp.oio.oauth.core.event.update;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import indi.dbfmp.oio.oauth.core.entity.*;
import indi.dbfmp.oio.oauth.core.innerService.*;
import lombok.extern.slf4j.Slf4j;
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
public class GroupsUpdateEventListener {

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

    @Async("updateMiddleTablePool")
    @EventListener
    public void groupUpdateEvent(GroupsUpdateEvent groupsUpdateEvent) {
        log.info("收到group更新事件,groupsUpdateEvent:{}",groupsUpdateEvent);
        if (StrUtil.isBlank(groupsUpdateEvent.getId())) {
            log.warn("groupsUpdateEvent,警告！更新事件主键id为空！");
            return;
        }
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
        log.info("groupsUpdateEvent,更新结束");
    }

}
