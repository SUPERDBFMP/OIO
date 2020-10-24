package indi.dbfmp.oio.oauth.core.event.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  权限更新事件
 * </p>
 *
 * @author dbfmp
 * @name: PermissionUpdateEvent
 * @since 2020/10/24 10:49 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionUpdateEvent {

    /**
     * 主键id
     */
    private String id;
    /**
     * 权限英文代码
     */
    private String permissionCode;

    /**
     * 授权名称
     */
    private String permissionName;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;
}
