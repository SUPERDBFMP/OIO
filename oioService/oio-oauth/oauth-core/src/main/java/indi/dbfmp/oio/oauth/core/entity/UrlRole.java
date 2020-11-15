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
 * url-角色表
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
public class UrlRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名
     */
    private String orgName;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * url
     */
    private String url;


    public static final String ORG_ID = "org_id";

    public static final String ROLE_ID = "role_id";

    public static final String ORG_NAME = "org_name";

    public static final String ROLE_NAME = "role_name";

    public static final String URL = "url";

}
