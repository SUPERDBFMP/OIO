package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  用户角色权限
 * </p>
 *
 * @author dbfmp
 * @name: UserRoleCondition
 * @since 2020/10/25 10:34 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserRoleCondition extends BaseCondition{

    /**
     * 角色id
     */
    @WrapperCondition(columnName = UserRole.ROLE_ID)
    private String roleId;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = UserRole.ROLE_NAME)
    private String roleName;

    /**
     * 用户id
     */
    @WrapperCondition(columnName = UserRole.USER_ID)
    private String userId;

    /**
     * 分组id
     */
    @WrapperCondition(columnName = UserRole.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = UserRole.GROUP_NAME)
    private String groupName;

}
