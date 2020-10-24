package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import indi.dbfmp.oio.oauth.core.entity.Position;
import indi.dbfmp.validator.core.group.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *   职位查询条件
 * </p>
 *
 * @author dbfmp
 * @name: PositionCondition
 * @since 2020/10/24 9:44 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class PositionCondition extends BaseCondition{

    /**
     * 职位名称
     */
    @WrapperCondition(columnName = Position.POSITION_NAME)
    private String positionName;

    /**
     * 职位编码
     */
    @WrapperCondition(columnName = Position.POSITION_CODE)
    private String positionCode;

    /**
     * 组织id
     */
    @WrapperCondition(columnName = Position.ORG_ID)
    private String orgId;

}
