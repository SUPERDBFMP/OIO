package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.enums.WrapperTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import indi.dbfmp.oio.oauth.core.entity.Org;

/**
 * <p>
 *  组织机构查询条件
 * </p>
 *
 * @author dbfmp
 * @name: OrgConfition
 * @since 2020/10/22 11:23 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class OrgCondition extends BaseCondition{

    @WrapperCondition(columnName = Org.ORG_NAME,wrapperType = WrapperTypes.EQ)
    String orgName;

    @WrapperCondition(columnName = Org.ORG_CODE,wrapperType = WrapperTypes.EQ)
    String orgCode;

    @WrapperCondition(columnName = Org.ORG_TYPE,wrapperType = WrapperTypes.EQ)
    String orgType;

}
