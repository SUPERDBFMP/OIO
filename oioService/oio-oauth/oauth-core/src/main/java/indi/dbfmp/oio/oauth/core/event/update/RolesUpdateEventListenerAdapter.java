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
 * @name: RolesUpdateEventListenerAdpter
 * @since 2020/10/30 12:19 上午
 */
@Component
public class RolesUpdateEventListenerAdapter {

    @Autowired
    private RolesUpdateEventListener rolesUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(RolesUpdateEvent updateEvent) {
        rolesUpdateEventListener.eventAction(updateEvent);
    }

}
