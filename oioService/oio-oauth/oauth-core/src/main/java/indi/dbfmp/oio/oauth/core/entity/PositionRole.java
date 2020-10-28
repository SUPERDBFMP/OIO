package indi.dbfmp.oio.oauth.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 职位-角色表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-25
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PositionRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private String positionId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 组织名
     */
    private String roleName;

    /**
     * 角色名
     */
    private String positionName;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组名
     */
    private String groupName;


    public static final String POSITION_ID = "position_id";

    public static final String ROLE_ID = "role_id";

    public static final String ROLE_NAME = "role_name";

    public static final String POSITION_NAME = "position_name";

    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

}
