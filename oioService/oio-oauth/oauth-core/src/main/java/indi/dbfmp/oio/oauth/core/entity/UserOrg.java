package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户-组织表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
