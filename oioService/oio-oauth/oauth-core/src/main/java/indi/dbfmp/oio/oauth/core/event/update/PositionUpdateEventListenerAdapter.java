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
 * @name: PositionUpdateEventListenerAdapter
 * @since 2020/10/30 12:25 上午
 */
@Component
public class PositionUpdateEventListenerAdapter {

    @Autowired
    private PositionUpdateEventListener positionUpdateEventListener;

    @Async("updateMiddleTablePool")
    @EventListener
    public void eventAction(PositionUpdateEvent updateEvent) {
        positionUpdateEventListener.eventAction(updateEvent);
    }

}
