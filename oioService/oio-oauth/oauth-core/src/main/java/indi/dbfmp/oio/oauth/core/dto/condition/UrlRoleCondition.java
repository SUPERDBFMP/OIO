package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.UrlRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  url-角色条件
 * </p>
 *
 * @author dbfmp
 * @name: UrlRoleCondition
 * @since 2020/10/25 10:10 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UrlRoleCondition extends BaseCondition{

    /**
     * 分组id
     */
    @WrapperCondition(columnName = UrlRole.GROUP_ID)
    private String groupId;

    /**
     * 角色id
     */
    @WrapperCondition(columnName = UrlRole.ROLE_ID)
    private String roleId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = UrlRole.GROUP_NAME)
    private String groupName;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = UrlRole.ROLE_NAME)
    private String roleName;

    /**
     * url
     */
    @WrapperCondition(columnName = UrlRole.URL)
    private String url;

}
