package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  登录获取授权码dto
 * </p>
 *
 * @author dbfmp
 * @name: loginDto
 * @since 2020/10/11 7:00 下午
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {

    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 使用加密的密码
     */
    @NotBlank(message = "密码不能为空")
    private String pwd;

    @NotBlank(message = "clientId不能为空")
    private String clientId;

    @NotBlank(message = "redirectUrl不能为空")
    private String redirectUrl;

    @NotBlank(message = "appType不能为空")
    private String appType;

}
