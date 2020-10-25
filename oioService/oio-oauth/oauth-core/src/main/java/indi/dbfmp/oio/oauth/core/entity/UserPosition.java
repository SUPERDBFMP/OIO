package indi.dbfmp.oio.oauth.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用户-职位表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-25
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserPosition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 职位id
     */
    private String positionId;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 分组ID
     */
    private String groupId;

    /**
     * 分组名
     */
    private String groupName;


    public static final String USER_ID = "user_id";

    public static final String POSITION_ID = "position_id";

    public static final String POSITION_NAME = "position_name";

    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

}
