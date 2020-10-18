package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户-职位表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPosition extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 职位id
     */
    private Integer positionId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 职位名称
     */
    private String positionName;


}
