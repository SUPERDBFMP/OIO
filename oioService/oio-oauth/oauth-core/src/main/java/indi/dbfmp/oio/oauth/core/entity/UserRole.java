package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;

/**
 * <p>
 * 用户-角色表
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
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 分组id
     */
    private String groupId;

    private String groupName;


}
