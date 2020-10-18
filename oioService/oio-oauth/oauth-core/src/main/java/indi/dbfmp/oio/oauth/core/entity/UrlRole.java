package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * url-角色表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UrlRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * url
     */
    private String url;


}
