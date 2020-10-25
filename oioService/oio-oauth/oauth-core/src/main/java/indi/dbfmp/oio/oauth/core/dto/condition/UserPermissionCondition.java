package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.UserPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  用户权限条件
 * </p>
 *
 * @author dbfmp
 * @name: UserPermissionCondition
 * @since 2020/10/25 10:23 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserPermissionCondition extends BaseCondition{

    /**
     * 用户id
     */
    @WrapperCondition(columnName = UserPermission.USER_ID)
    private String userId;

    /**
     * 权限id
     */
    @WrapperCondition(columnName = UserPermission.PERMISSION_ID)
    private String permissionId;

    /**
     * 权限名
     */
    @WrapperCondition(columnName = UserPermission.PERMISSION_NAME)
    private String permissionName;

    /**
     * 分组id
     */
    @WrapperCondition(columnName = UserPermission.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = UserPermission.GROUP_NAME)
    private String groupName;

}
