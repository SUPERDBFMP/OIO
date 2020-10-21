package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;

/**
 * <p>
 * 用户-职位表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPosition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位id
     */
    private String positionId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 职位名称
     */
    private String positionName;

    private String groupId;

    private String groupName;


}
