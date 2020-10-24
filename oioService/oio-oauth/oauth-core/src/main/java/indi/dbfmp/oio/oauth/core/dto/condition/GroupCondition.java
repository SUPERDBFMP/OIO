package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <p>
 *  分组查询条件
 * </p>
 *
 * @author dbfmp
 * @name: GroupCondition
 * @since 2020/10/24 9:17 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class GroupCondition extends BaseCondition{

    /**
     * 组名称
     */
    @WrapperCondition(columnName = Groups.GROUP_NAME)
    private String groupName;

    /**
     * 组编码
     */
    @WrapperCondition(columnName = Groups.GROUP_NAME)
    private String groupCode;

    /**
     * 组织机构id
     */
    @WrapperCondition(columnName = Groups.ORG_ID)
    private String orgId;

}
