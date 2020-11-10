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
 * @name: GrantNewRolesDto
 * @since 2020/11/3 10:49 下午
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GrantNewRolesDto implements Serializable {

    private static final long serialVersionUID = -4152364948588641374L;

    @NotBlank(message = "用户ID不能为空")
    String userId;

    @NotEmpty(message = "角色列表不能为空")
    List<String> roleIdList;

}
