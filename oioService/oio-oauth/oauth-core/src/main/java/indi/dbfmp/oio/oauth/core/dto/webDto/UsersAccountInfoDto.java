package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: UsersAccountInfo
 * @since 2021/3/2 下午9:31
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class UsersAccountInfoDto implements Serializable {

    private static final long serialVersionUID = -8387797631666276727L;

    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户名称,默认为手机号
     */
    private String nickName;

    /**
     * 用手机号,可用来登录
     */
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
     * 是否开启两步认证：1是，0否
     */
    private Integer openTowStepAuth;

}
