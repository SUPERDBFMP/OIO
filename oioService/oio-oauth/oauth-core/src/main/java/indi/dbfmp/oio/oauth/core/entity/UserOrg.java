package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 用户-组织表
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
public class UserOrg extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 组织名称
     */
    private String orgName;


}
