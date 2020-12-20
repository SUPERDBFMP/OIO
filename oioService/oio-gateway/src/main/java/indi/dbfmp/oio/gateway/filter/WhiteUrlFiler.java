package indi.dbfmp.oio.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import indi.dbfmp.oio.gateway.config.OioGatewayConfig;
import indi.dbfmp.web.common.dto.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  百名单URL过滤
 * </p>
 *
 * @author dbfmp
 * @name: WhiteUrlFiler
 * @since 2020/10/11 3:09 下午
 */
@Order(-1)
@Component
public class WhiteUrlFiler implements WebFilter {

    @Autowired
    private OioGatewayConfig oioGatewayConfig;



    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String s : oioGatewayConfig.getWhiteUrlList()) {
            if (pathMatcher.match(s, uri.getPath())) {
                request = serverWebExchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, "").header("whiteUrl","pass").build();
                serverWebExchange = serverWebExchange.mutate().request(request).build();
                return webFilterChain.filter(serverWebExchange);
            }
        }
        //不在白名单中，检查是否有Authorization
        String token = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            //无权访问
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.FORBIDDEN);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body= JSONUtil.toJsonStr(CommonResult.forbidden("没有权限访问哦～"));
            DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
