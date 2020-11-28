package inid.dbfmp.oauth.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *  角色检查
 * </p>
 *
 * @author dbfmp
 * @name: RoleCheck
 * @since 2020/10/22 10:14 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCheck implements Serializable {

    private static final long serialVersionUID = -5913932324694776726L;

    @NotBlank(message = "角色检查URL不能为空")
    private String url;
    @NotBlank(message = "角色检查用户ID不能为空")
    private String userId;
    @NotBlank(message = "组织机构ID不能为空")
    private String orgId;
}
