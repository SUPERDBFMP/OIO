package indi.dbfmp.oio.oauth.core.event.update;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  角色更新事件
 * </p>
 *
 * @author dbfmp
 * @name: RolesUpdateEvent
 * @since 2020/10/24 10:12 下午
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RolesUpdateEvent {

    private String id;
    /**
     * 角色英文编码
    private String roleCode;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 组织机构id
     */
    private String orgId;

    /**
     * 组织机构名称
     */
    private String orgName;
}
