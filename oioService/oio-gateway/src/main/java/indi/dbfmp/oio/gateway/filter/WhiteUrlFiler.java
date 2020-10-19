package indi.dbfmp.oio.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import indi.dbfmp.web.common.dto.CommonResult;
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
import java.util.HashSet;

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

    private HashSet<String> whiteUrlSet = new HashSet<>();

    @PostConstruct
    public void setWhiteUrlSet() {
        whiteUrlSet.add("/test/**");
        whiteUrlSet.add("/*/token/get");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String s : whiteUrlSet) {
            if (pathMatcher.match(s, uri.getPath())) {
                request = serverWebExchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, "").build();
                serverWebExchange = serverWebExchange.mutate().request(request).build();
                return webFilterChain.filter(serverWebExchange);
            }
        }
        //不在白名单中，检查是否有Authorization
        String token = serverWebExchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            //无权访问
            ServerHttpResponse response = serverWebExchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body= JSONUtil.toJsonStr(CommonResult.forbidden("没有权限访问哦～"));
            DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        return webFilterChain.filter(serverWebExchange);
    }
}
