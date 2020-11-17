package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: RolePermissionDto
 * @since 2020/10/28 11:23 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RolePermissionDto implements Serializable {

    private static final long serialVersionUID = -4515092546120640992L;

    @NotBlank(message = "角色ID不能为空")
    String roleId;
    @NotEmpty(message = "权限ID列表不能为空")
    List<String> permissionIdList;
}
