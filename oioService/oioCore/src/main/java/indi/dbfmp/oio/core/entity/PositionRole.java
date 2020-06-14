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
public class PositionRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private Integer positionId;

    /**
     * 角色id
     */
    private Integer roleId;

    /**
     * 组织名
     */
    private String roleName;

    /**
     * 角色名
     */
    private String positionName;

    /**
     * 分组id
     */
    private Integer groupId;


}
