package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.GroupUser;
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
 * @name: GroupUserCondition
 * @since 2020/11/18 下午10:05
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class GroupUserCondition extends BaseCondition{

    /**
     * 分组id
     */
    @WrapperCondition(columnName = GroupUser.GROUP_ID)
    private String groupId;

    /**
     * 分组名
     */
    @WrapperCondition(columnName = GroupUser.GROUP_NAME)
    private String groupName;

    /**
     * 用户id
     */
    @WrapperCondition(columnName = GroupUser.USER_ID)
    private String userId;

    /**
     * 用户名
     */
    @WrapperCondition(columnName = GroupUser.USER_NAME)
    private String userName;

}
