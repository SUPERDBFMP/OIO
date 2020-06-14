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
public class UserOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private Integer orgId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 组织名称
     */
    private String orgName;


}
