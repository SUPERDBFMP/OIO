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
 * @name: OrgUpdateEventListenerAdapter
 * @since 2020/10/30 12:23 上午
 */
@Component
public class OrgUpdateEventListenerAdapter {

    @Autowired
    private OrgUpdateEventListener orgUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(OrgUpdateEvent orgUpdateEvent) {
        orgUpdateEventListener.eventAction(orgUpdateEvent);
    }

}
