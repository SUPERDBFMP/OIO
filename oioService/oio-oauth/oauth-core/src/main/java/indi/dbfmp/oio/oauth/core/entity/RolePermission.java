package indi.dbfmp.oio.oauth.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 角色-权限表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-24
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名
     */
    private String orgName;


    public static final String ROLE_ID = "role_id";

    public static final String ROLE_NAME = "role_name";

    public static final String PERMISSION_ID = "permission_id";

    public static final String PERMISSION_NAME = "permission_name";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
