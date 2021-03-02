package indi.dbfmp.oio.oauth.core.service.dubboServiceImpl;

import indi.dbfmp.oio.oauth.core.service.impl.UserService;
import indi.dbfmp.web.common.exception.DubboCommonException;
import inid.dbfmp.oauth.api.exception.CommonException;
import inid.dbfmp.oauth.api.service.OioUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioUserServiceImpl
 * @since 2021/3/1 下午9:12
 */
@RequiredArgsConstructor
@Slf4j
@Component
@Service(group = "${spring.profiles.active}")
public class OioUserServiceImpl implements OioUserService {

    private final UserService userService;

    /**
     * 删除用户账号
     *
     * @param delUserId 删除用户的ID
     * @param code   操作用户两步认证码
     */
    @Override
    public void delUser(String opUserId,String delUserId, int code) throws DubboCommonException {
        try {
            userService.delUser(opUserId, delUserId, code);
        } catch (CommonException e) {
            throw new DubboCommonException(e.getMessage());
        }

    }
}
