package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 职位-角色表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
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