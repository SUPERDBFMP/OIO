package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: ResetPwdDto
 * @since 2020/11/28 下午5:36
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ResetPwdDto implements Serializable {

    private static final long serialVersionUID = 3072313221623909545L;

    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
