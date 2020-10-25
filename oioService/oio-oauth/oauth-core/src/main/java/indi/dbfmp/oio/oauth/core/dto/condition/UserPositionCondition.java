package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.UserPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  用户职位条件
 * </p>
 *
 * @author dbfmp
 * @name: UserPositionCondition
 * @since 2020/10/25 10:28 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class UserPositionCondition extends BaseCondition{

    /**
     * 用户id
     */
    @WrapperCondition(columnName = UserPosition.USER_ID)
    private String userId;

    /**
     * 职位id
     */
    @WrapperCondition(columnName = UserPosition.POSITION_ID)
    private String positionId;

    /**
     * 职位名称
     */
    @WrapperCondition(columnName = UserPosition.POSITION_NAME)
    private String positionName;

    /**
     * 分组ID
     */
    @WrapperCondition(columnName = UserPosition.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = UserPosition.GROUP_NAME)
    private String groupName;

}
