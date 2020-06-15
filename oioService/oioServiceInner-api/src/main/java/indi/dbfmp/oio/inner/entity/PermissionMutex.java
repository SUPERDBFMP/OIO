package indi.dbfmp.oio.inner.entity;

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
public class PermissionMutex extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 权限1
     */
    private Integer firstRoleId;

    /**
     * 权限2
     */
    private Integer secondRoleId;


}
