package inid.dbfmp.oauth.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: RegisterUserResp
 * @since 2020/12/2 下午10:20
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserResp implements Serializable {

    private static final long serialVersionUID = -588850023085960457L;
    /**
     * 用手机号,可用来登录
     */
    @NotBlank(message = "注册手机号不能为空")
    private String phone;

    /**
     * 用户号
     */
    @NotBlank(message = "用户ID不能为空")
    private String userId;
}
