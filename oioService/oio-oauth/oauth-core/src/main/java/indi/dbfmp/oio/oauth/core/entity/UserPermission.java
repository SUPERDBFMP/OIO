package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户-权限表
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserPermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 权限id
     */
    private String permissionId;

    /**
     * 权限名
     */
    private String permissionName;

    /**
     * 分组id
     */
    private String groupId;


}