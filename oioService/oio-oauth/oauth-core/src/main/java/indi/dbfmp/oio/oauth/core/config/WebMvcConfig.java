package indi.dbfmp.oio.oauth.core.config;

import indi.dbfmp.oio.oauth.core.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * mvc配置
 * </p>
 *
 * @author dbfmp
 * @name: WebMvcConfig
 * @since 2020/12/20 下午7:39
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/**");
    }
}
