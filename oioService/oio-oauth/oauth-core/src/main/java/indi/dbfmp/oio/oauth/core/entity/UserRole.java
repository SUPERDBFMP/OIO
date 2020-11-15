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
 * 用户-角色表
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
public class UserRole extends BaseEntity {

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
     * 用户id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

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

    public static final String USER_ID = "user_id";

    public static final String USER_NAME = "user_name";

    public static final String ORG_ID = "org_id";

    public static final String ORG_NAME = "org_name";

}
