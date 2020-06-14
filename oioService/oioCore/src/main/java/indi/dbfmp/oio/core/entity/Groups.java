package indi.dbfmp.oio.core.entity;

import indi.dbfmp.oio.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author dbfmp
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
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
