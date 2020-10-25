package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.PositionRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  职位角色条件
 * </p>
 *
 * @author dbfmp
 * @name: PositionRoleCondition
 * @since 2020/10/25 12:10 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PositionRoleCondition extends BaseCondition{


    /**
     * 组织id
     */
    @WrapperCondition(columnName = PositionRole.POSITION_ID)
    private String positionId;

    /**
     * 角色id
     */
    @WrapperCondition(columnName = PositionRole.ROLE_ID)
    private String roleId;

    /**
     * 组织名
     */
    @WrapperCondition(columnName = PositionRole.ROLE_NAME)
    private String roleName;

    /**
     * 角色名
     */
    @WrapperCondition(columnName = PositionRole.POSITION_NAME)
    private String positionName;

    /**
     * 分组id
     */
    @WrapperCondition(columnName = PositionRole.GROUP_ID)
    private String groupId;

}
