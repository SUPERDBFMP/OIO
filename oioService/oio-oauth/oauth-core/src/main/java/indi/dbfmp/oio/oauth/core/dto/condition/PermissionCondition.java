package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  权限条件
 * </p>
 *
 * @author dbfmp
 * @name: PermissionCondition
 * @since 2020/10/24 11:06 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PermissionCondition extends BaseCondition{

    /**
     * 权限英文代码
     */
    @WrapperCondition(columnName = Permission.PERMISSION_CODE)
    private String permissionCode;

    /**
     * 授权名称
     */
    @WrapperCondition(columnName = Permission.PERMISSION_NAME)
    private String permissionName;

    /**
     * 组织机构id
     */
    @WrapperCondition(columnName = Permission.ORG_ID)
    private String orgId;

    /**
     * 组织机构名称
     */
    @WrapperCondition(columnName = Permission.ORG_NAME)
    private String orgName;

}
