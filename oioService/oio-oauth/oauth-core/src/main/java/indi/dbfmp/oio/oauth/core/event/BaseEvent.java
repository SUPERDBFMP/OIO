package indi.dbfmp.oio.oauth.core.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p>
 *  基础event
 * </p>
 *
 * @author dbfmp
 * @name: BaseEvent
 * @since 2020/10/29 8:38 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseEvent implements Serializable {

    private String eventId;

}
