package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;

/**
 * <p>
 * 组织机构-角色表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class OrgRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组织id
     */
    private String orgId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 组织名
     */
    private String orgName;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 分组id
     */
    private Integer groupId;


}
