package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import java.time.LocalDateTime;
import java.util.Date;

import indi.dbfmp.validator.core.group.AddGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author dbfmp
 * @since 2020-11-28
 */
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class Users extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码不能为空",groups = AddGroup.class)
    private String password;

    /**
     * 用户名称,默认为手机号
     */
    private String nickName;

    /**
     * 用手机号,可用来登录
     */
    @NotBlank(message = "手机号不能为空",groups = AddGroup.class)
    private String phone;

    /**
     * 邮箱账号
     */
    private String email;

    /**
     * 用户状态,vip等等
     */
    private String status;

    /**
     * 用户最后登录的ip
     */
    private String loginIp;

    /**
     * 用户最后登录的时间
     */
    private Date loginDate;

    /**
     * 用户是否可登录,0:可登录,1不可登录
     */
    private Integer loginFlag;

    /**
     * 是否默认密码：1是，0否
     */
    private Integer defaultPwd;

    /**
     * 是否开启两步认证：1是，0否
     */
    private Integer openTowStepAuth;

    /**
     * 两步认证秘钥
     */
    private String authKey;

    /**
     * 两步认证URL
     */
    private String authUrl;




    public static final String USER_ID = "user_id";

    public static final String PASSWORD = "password";

    public static final String NICK_NAME = "nick_name";

    public static final String PHONE = "phone";

    public static final String EMAIL = "email";

    public static final String STATUS = "status";

    public static final String LOGIN_IP = "login_ip";

    public static final String LOGIN_DATE = "login_date";

    public static final String LOGIN_FLAG = "login_flag";

    public static final String DEFAULT_PWD = "default_pwd";

    public static final String OPEN_TOW_STEP_AUTH = "open_tow_step_auth";

    public static final String AUTH_KEY = "auth_Key";

    public static final String AUTH_URL = "auth_Url";

}
