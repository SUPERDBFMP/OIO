package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * url-权限表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UrlPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 角色id
     */
    private String permissionId;

    /**
     * 角色名
     */
    private String permissionName;

    /**
     * url
     */
    private String url;


}
