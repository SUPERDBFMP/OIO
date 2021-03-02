package inid.dbfmp.oauth.api.service;

import indi.dbfmp.web.common.exception.DubboCommonException;
import inid.dbfmp.oauth.api.exception.CommonException;

/**
 * <p>
 *  oio用户服务
 * </p>
 *
 * @author dbfmp
 * @name: OioUserService
 * @since 2021/3/1 下午9:11
 */
public interface OioUserService {

    /**
     * 删除用户账号
     * @param opUserId 操作用户ID
     * @param delUserId 删除的用户ID
     * @param code 操作用户两步认证码
     */
    void delUser(String opUserId,String delUserId, int code) throws DubboCommonException;

}
