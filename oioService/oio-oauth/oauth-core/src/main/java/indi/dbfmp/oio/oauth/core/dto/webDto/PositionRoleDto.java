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
 * @name: PositionRoleDto
 * @since 2020/10/28 11:18 下午
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PositionRoleDto implements Serializable {

    private static final long serialVersionUID = -3934351027662716162L;
    @NotBlank(message = "分组ID不能为空")
    String groupId;
    @NotBlank(message = "职位ID不能为空")
    String positionId;
    @NotEmpty(message = "角色ID列表不能为空")
    List<String> roleIdList;
}
