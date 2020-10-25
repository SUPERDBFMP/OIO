package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.PositionGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  角色分组条件
 * </p>
 *
 * @author dbfmp
 * @name: PositionGroupCondition
 * @since 2020/10/25 11:47 上午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PositionGroupCondition extends BaseCondition{


    /**
     * 组织id
     */
    @WrapperCondition(columnName = PositionGroup.POSITION_ID)
    private String positionId;

    /**
     * 组织名
     */
    @WrapperCondition(columnName = PositionGroup.POSITION_NAME)
    private String positionName;

    /**
     * 分组id
     */
    @WrapperCondition(columnName = PositionGroup.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = PositionGroup.GROUP_NAME)
    private String groupName;

}
