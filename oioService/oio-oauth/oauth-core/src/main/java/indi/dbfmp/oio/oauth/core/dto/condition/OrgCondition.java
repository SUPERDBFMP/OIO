package indi.dbfmp.oio.oauth.core.dto.condition;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

    String orgName;

    String orgCode;

    String orgType;

}
