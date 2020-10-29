package indi.dbfmp.oio.oauth.core.event.update;

import indi.dbfmp.oio.oauth.core.event.BaseEvent;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <p>
 *  组织机构更新事件
 * </p>
 *
 * @author dbfmp
 * @name: OrgUpdateEvent
 * @since 2020/10/24 8:22 下午
 */
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgUpdateEvent extends BaseEvent {

    /**
     * 主键id
     */
    private String id;

    private String orgName;

    private String orgCode;

    private String orgType;


}
