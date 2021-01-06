package indi.dbfmp.oio.oauth.core.dto.condition;

import indi.dbfmp.oio.oauth.core.annotaion.WrapperCondition;
import indi.dbfmp.oio.oauth.core.entity.Client;
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
 * @name: ClientCondition
 * @since 2020/12/21 下午11:40
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class ClientCondition extends BaseCondition{

    /**
     * 系统名
     */
    @WrapperCondition(columnName = Client.CLIENT_NAME)
    private String clientName;

    /**
     * 系统id
     */
    @WrapperCondition(columnName = Client.CLIENT_ID)
    private String clientId;

    /**
     * 应用是否可调用,0:不可调用,1：可调用
     */
    @WrapperCondition(columnName = Client.ACCESS_FLAG)
    private Integer accessFlag;

    /**
     * 组织机构id
     */
    @WrapperCondition(columnName = Client.ORG_ID)
    private String orgId;
}
