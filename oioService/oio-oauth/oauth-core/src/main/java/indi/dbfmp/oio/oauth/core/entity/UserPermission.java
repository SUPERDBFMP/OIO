package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用户-权限表
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
public class UserPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 分组名
     */
    private String groupName;


    public static final String USER_ID = "user_id";

    public static final String PERMISSION_ID = "permission_id";

    public static final String PERMISSION_NAME = "permission_name";

    public static final String GROUP_ID = "group_id";

    public static final String GROUP_NAME = "group_name";

}
