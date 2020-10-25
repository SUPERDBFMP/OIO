package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.RolePermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  角色权限条件
 * </p>
 *
 * @author dbfmp
 * @name: RolePermissionCondition
 * @since 2020/10/25 12:33 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RolePermissionCondition extends BaseCondition{

    /**
     * 角色id
     */
    @WrapperCondition(columnName = RolePermission.ROLE_ID)
    private String roleId;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = RolePermission.ROLE_NAME)
    private String roleName;

    /**
     * 权限id
     */
    @WrapperCondition(columnName = RolePermission.PERMISSION_ID)
    private String permissionId;

    /**
     * 权限名
     */
    @WrapperCondition(columnName = RolePermission.PERMISSION_NAME)
    private String permissionName;

    /**
     * 分组id
     */
    @WrapperCondition(columnName = RolePermission.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = RolePermission.GROUP_NAME)
    private String groupName;

}
