package indi.dbfmp.oio.core.entity;

import indi.dbfmp.oio.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dbfmp
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 授权名称
     */
    private String permissionName;

    /**
     * 授权描述
     */
    private String permissionDesc;

    /**
     * 分组id
     */
    private Integer groupId;


}
