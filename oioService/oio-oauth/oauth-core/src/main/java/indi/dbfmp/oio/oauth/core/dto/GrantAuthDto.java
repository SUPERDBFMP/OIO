package indi.dbfmp.oio.oauth.core.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 *  授权dto
 * </p>
 *
 * @author dbfmp
 * @name: GrantAuthDto
 * @since 2020/10/20 8:47 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrantAuthDto {

    @NotBlank(message = "用户ID不能为空")
    private String userId;
    /**
     * 组织机构id
     */
    @NotBlank(message = "组织机构ID不能为空")
    private String orgId;
    @NotBlank(message = "分组ID不能为空")
    private String groupId;
    @NotEmpty(message = "职位ID列表不能为空")
    private List<String> positionIdList;

    private List<String> roleIdList;

}
