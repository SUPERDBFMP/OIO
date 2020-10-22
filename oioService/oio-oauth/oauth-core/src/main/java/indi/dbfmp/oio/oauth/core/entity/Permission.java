package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 权限详情表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 授权名称
     */
    private String permissionName;

    /**
     * 授权英文代码
     */
    private String permissionCode;


}
