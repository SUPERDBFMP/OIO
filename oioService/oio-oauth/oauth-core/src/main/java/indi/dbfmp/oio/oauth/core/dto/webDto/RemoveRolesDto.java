package indi.dbfmp.oio.oauth.core.dto.webDto;

import indi.dbfmp.oio.oauth.core.dto.UserRoleGroupDto;
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
 * @name: removeRolesDto
 * @since 2020/11/3 10:54 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RemoveRolesDto implements Serializable {

    private static final long serialVersionUID = -6260209695564562508L;
    @NotBlank(message = "用户ID不能为空")
    String userId;
    @NotEmpty(message = "角色列表不能为空")
    List<UserRoleGroupDto> userRoleGroupDtoList;
}
