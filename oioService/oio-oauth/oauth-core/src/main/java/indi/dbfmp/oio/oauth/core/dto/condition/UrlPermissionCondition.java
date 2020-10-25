package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.UrlPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: UrlPermissionCondition
 * @since 2020/10/25 12:43 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UrlPermissionCondition extends BaseCondition{

    /**
     * 分组id
     */
    @WrapperCondition(columnName = UrlPermission.GROUP_ID)
    private String groupId;

    /**
     * 角色id
     */
    @WrapperCondition(columnName = UrlPermission.PERMISSION_ID)
    private String permissionId;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = UrlPermission.PERMISSION_NAME)
    private String permissionName;

    /**
     * url
     */
    @WrapperCondition(columnName = UrlPermission.URL)
    private String url;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = UrlPermission.GROUP_NAME)
    private String groupName;

}
