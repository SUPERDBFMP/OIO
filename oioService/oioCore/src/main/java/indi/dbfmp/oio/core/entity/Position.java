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
public class Position extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位名称
     */
    private String positionName;

    /**
     * 职位编码
     */
    private String positionCode;

    /**
     * 上级id
     */
    private Integer superiorId;

    /**
     * 组织id
     */
    private String orgId;


}
