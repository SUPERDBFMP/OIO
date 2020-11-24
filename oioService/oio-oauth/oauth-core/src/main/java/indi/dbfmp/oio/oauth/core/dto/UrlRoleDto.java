package indi.dbfmp.oio.oauth.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: UrlRoleGroupDto
 * @since 2020/11/11 10:11 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UrlRoleDto {

    @NotBlank(message = "角色ID不能为空",groups = RoleIdCheck.class)
    private String roleId;

    @NotBlank(message = "分组ID不能为空",groups = GroupIdCheck.class)
    private String groupId;

    @NotBlank(message = "URL不能为空")
    private String url;

    public interface RoleIdCheck{}
    public interface GroupIdCheck{}

}
