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
 * @name: UserRolePermissionUpdateEventListenerAdapter
 * @since 2020/11/2 11:10 下午
 */
@Component
public class UserRolePermissionUpdateEventListenerAdapter {

    @Autowired
    private UserRolePermissionUpdateEventListener userRolePermissionUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(UserRolePermissionUpdateEvent updateEvent) {
        userRolePermissionUpdateEventListener.eventAction(updateEvent);
    }
}
