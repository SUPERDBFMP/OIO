package indi.dbfmp.oio.oauth.core.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.Payload;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  用户信息拦截器
 * </p>
 *
 * @author dbfmp
 * @name: AuthlerInterceptor
 * @since 2020/12/20 下午7:24
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userHeader = request.getHeader("user");
        if (StrUtil.isNotBlank(userHeader)) {
            PayloadDto userInfo = JSONObject.parseObject(userHeader,PayloadDto.class);
            UserInfoContext.setUserInfoContext(userInfo);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserInfoContext.removeUserInfo();
    }
}
