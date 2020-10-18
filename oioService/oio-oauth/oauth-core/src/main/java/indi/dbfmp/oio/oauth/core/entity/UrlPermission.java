package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * url-权限表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UrlPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 角色id
     */
    private Integer permissonId;

    /**
     * 角色名
     */
    private String permissonName;

    /**
     * url
     */
    private String url;


}
