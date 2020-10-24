package indi.dbfmp.oio.oauth.core.event.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  组织机构更新事件
 * </p>
 *
 * @author dbfmp
 * @name: OrgUpdateEvent
 * @since 2020/10/24 8:22 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrgUpdateEvent {

    /**
     * 主键id
     */
    private String id;

    private String orgName;

    private String orgCode;

    private String orgType;


}
