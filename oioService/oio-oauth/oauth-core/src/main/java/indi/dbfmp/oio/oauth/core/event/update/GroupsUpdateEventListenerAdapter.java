package indi.dbfmp.oio.oauth.core.event.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: GroupsUpdateEventListenerAdapter
 * @since 2020/10/30 12:21 上午
 */
@Component
public class GroupsUpdateEventListenerAdapter {

    @Autowired
    private GroupsUpdateEventListener groupsUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(GroupsUpdateEvent groupsUpdateEvent) {
        groupsUpdateEventListener.eventAction(groupsUpdateEvent);
    }

}
