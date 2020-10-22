package inid.dbfmp.oauth.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *   权限检查
 * </p>
 *
 * @author dbfmp
 * @name: PermissionCheck
 * @since 2020/10/22 10:15 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionCheck implements Serializable {

    private static final long serialVersionUID = 879656264225402843L;

    @NotBlank(message = "角色检查URL不能为空")
    private String url;
    @NotBlank(message = "角色检查用户ID不能为空")
    private String userId;

}
