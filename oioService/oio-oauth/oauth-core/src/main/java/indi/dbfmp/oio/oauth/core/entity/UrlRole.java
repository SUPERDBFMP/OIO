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
     * 分组id
     */
    private String groupId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * url
     */
    private String url;


    public static final String GROUP_ID = "group_id";

    public static final String ROLE_ID = "role_id";

    public static final String GROUP_NAME = "group_name";

    public static final String ROLE_NAME = "role_name";

    public static final String URL = "url";

}
