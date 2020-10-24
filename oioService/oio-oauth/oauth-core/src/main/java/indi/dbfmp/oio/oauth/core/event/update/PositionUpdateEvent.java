package indi.dbfmp.oio.oauth.core.event.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  职位更新事件
 * </p>
 *
 * @author dbfmp
 * @name: PositionUpdateEvent
 * @since 2020/10/24 9:29 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PositionUpdateEvent {

    private String id;
    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 职位编码
     */
    private String positionCode;

    /**
     * 上级id
     */
    private Integer superiorId;

    /**
     * 组织id
     */
    private String orgId;

}
