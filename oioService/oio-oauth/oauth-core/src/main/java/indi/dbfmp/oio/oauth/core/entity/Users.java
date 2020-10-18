package indi.dbfmp.oio.oauth.core.entity;

import indi.dbfmp.oio.oauth.core.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author dbfmp
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Users extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户密码
     */
    private String password;

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
    private LocalDateTime loginDate;

    /**
     * 用户是否可登录,0:可登录,1不可登录
     */
    private String loginFlag;


}
