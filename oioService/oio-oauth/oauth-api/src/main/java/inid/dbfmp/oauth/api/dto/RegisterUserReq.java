package inid.dbfmp.oauth.api.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *  注册用户请求
 * </p>
 *
 * @author dbfmp
 * @name: RegisterUserDto
 * @since 2020/12/2 下午10:19
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserReq implements Serializable {

    private static final long serialVersionUID = 2208055603185427245L;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 用手机号,可用来登录
     */
    @NotBlank(message = "注册手机号不能为空")
    private String phone;

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 接入系统的appId
     */
    @NotBlank(message = "接入系统appId不能为空")
    private String appId;

    /**
     * 权限分组Id
     */
    @NotBlank(message = "权限分组Id不能为空")
    private String groupId;

    /**
     * 权限分组名称
     */
    private String groupName;

    /**
     * 是否默认密码：1是，0否
     */
    @NotNull(message = "是否使用默认密码不能为空")
    private Integer defaultPwd;

}
