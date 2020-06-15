package indi.dbfmp.oio.inner.entity;

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
