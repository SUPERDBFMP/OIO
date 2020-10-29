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
 * @name: PermissionUpdateEventListenerAdapter
 * @since 2020/10/30 12:24 上午
 */
@Component
public class PermissionUpdateEventListenerAdapter {

    @Autowired
    private PermissionUpdateEventListener permissionUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(PermissionUpdateEvent updateEvent) {
        permissionUpdateEventListener.eventAction(updateEvent);
    }

}
