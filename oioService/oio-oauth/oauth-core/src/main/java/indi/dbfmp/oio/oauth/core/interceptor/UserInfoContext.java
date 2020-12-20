package indi.dbfmp.oio.oauth.core.interceptor;

import inid.dbfmp.oauth.api.dto.PayloadDto;

/**
 * <p>
 *  用户上下文
 * </p>
 *
 * @author dbfmp
 * @name: UserInfoContext
 * @since 2020/12/20 下午7:33
 */
public class UserInfoContext {

    private static final ThreadLocal<PayloadDto> USER_INFO_CONTEXT = new ThreadLocal<>();


    public static void setUserInfoContext(PayloadDto payloadDto) {
        USER_INFO_CONTEXT.set(payloadDto);
    }

    public static PayloadDto getUserInfo() {
        return USER_INFO_CONTEXT.get();
    }

    public static void removeUserInfo() {
        USER_INFO_CONTEXT.remove();
    }

}
