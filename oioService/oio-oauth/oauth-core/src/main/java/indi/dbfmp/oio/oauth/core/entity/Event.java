package indi.dbfmp.oio.oauth.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 事件表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-29
 */
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Event extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 事件类型
     */
    private String eventType;

    /**
     * 事件参数
     */
    private String eventParams;

    /**
     * 上级组织id
     */
    private String eventBeanName;

    /**
     * 事件当前状态
     */
    private String eventStatus;


    public static final String EVENT_TYPE = "event_type";

    public static final String EVENT_PARAMS = "event_params";

    public static final String EVENT_BEAN_NAME = "event_bean_name";

    public static final String EVENT_STATUS = "event_status";

}
