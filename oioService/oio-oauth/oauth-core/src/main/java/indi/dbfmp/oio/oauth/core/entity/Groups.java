package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 分组详情表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Groups extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * 组编码
     */
    private String groupCode;


}
