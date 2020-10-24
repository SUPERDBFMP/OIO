package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import indi.dbfmp.oio.oauth.core.entity.Roles;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: RolesCondition
 * @since 2020/10/24 10:37 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class RolesCondition extends BaseCondition{

    /**
     * 角色英文编码
     */
    @WrapperCondition(columnName = Roles.ROLE_CODE)
    private String roleCode;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = Roles.ROLE_NAME)
    private String roleName;

    /**
     * 组织机构id
     */
    @WrapperCondition(columnName = Roles.ORG_ID)
    private String orgId;

    /**
     * 组织机构名称
     */
    @WrapperCondition(columnName = Roles.ORG_NAME)
    private String orgName;

}
