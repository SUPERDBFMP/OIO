package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.GroupRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  分组角色条件
 * </p>
 *
 * @author dbfmp
 * @name: GroupRoleCondtion
 * @since 2020/10/24 11:34 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class GroupRoleCondition extends BaseCondition{

    /**
     * 分组id
     */
    @WrapperCondition(columnName = GroupRole.GROUP_ID)
    private String groupId;

    /**
     * 角色id
     */
    @WrapperCondition(columnName = GroupRole.ROLE_ID)
    private String roleId;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = GroupRole.ROLE_NAME)
    private String roleName;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = GroupRole.GROUP_NAME)
    private String groupName;

}
