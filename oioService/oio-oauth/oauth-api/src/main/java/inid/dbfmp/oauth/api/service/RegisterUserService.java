package inid.dbfmp.oauth.api.service;

import inid.dbfmp.oauth.api.dto.RegisterUserReq;
import inid.dbfmp.oauth.api.dto.RegisterUserResp;

/**
 * <p>
 *  注册用户服务
 * </p>
 *
 * @author dbfmp
 * @name: RegisterUserSevice
 * @since 2020/12/2 下午10:17
 */
public interface RegisterUserService {

    /**
     * 注册新用户
     * @param req 注册请求
     * @return 注册响应
     */
    RegisterUserResp registerUser(RegisterUserReq req);

}
