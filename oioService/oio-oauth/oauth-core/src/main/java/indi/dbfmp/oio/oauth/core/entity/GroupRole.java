package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 * 分组-角色表
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
public class GroupRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private String groupId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 分组名
     */
    private String groupName;


}
