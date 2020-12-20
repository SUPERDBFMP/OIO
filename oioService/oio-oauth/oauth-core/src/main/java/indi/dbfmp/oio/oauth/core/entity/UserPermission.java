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
     * 用户id
     */
    private String userName;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 权限代码
     */
    private String permissionCode;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名
     */
    private String orgName;


    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String PERMISSION_ID = "permission_id";

    public static final String PERMISSION_NAME = "permission_name";

    public static final String PERMISSION_CODE = "permission_code";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
