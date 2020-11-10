package indi.dbfmp.oio.oauth.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *  用户角色分组dto
 * </p>
 *
 * @author dbfmp
 * @name: UserRoleGroupDto
 * @since 2020/11/3 10:14 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRoleGroupDto {

    private String roleId;

    private String groupId;

}
