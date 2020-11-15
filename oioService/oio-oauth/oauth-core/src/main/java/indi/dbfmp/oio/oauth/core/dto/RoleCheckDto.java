package indi.dbfmp.oio.oauth.core.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  角色检查Dto
 * </p>
 *
 * @author dbfmp
 * @name: RoleCheckDto
 * @since 2020/10/18 10:04 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleCheckDto {

    @NotBlank(message = "角色检查URL不能为空")
    private String url;
    @NotBlank(message = "角色检查用户ID不能为空")
    private String userId;
    @NotBlank(message = "组织机构ID不能为空")
    private String orgId;

}
