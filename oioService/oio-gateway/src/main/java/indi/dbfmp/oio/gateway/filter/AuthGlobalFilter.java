package indi.dbfmp.oio.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import inid.dbfmp.common.dto.CommonResult;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * <p>
 *  全局权限过滤器
 * </p>
 *
 * @author dbfmp
 * @name: AuthGlobalFilter
 * @since 2020/10/11 2:49 下午
 */
@Slf4j
//过滤器优先级
@Order(0)
@Component
public class AuthGlobalFilter implements GlobalFilter {

    @Reference(check = false,group = "${spring.profiles.active}")
    private JwtTokenService jwtTokenService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StrUtil.isBlank(token)) {
            return chain.filter(exchange);
        }
        //调用服务校验token
        try {
            PayloadDto payloadDto = jwtTokenService.verifyAndGetPayloadDto(token);
            ServerHttpRequest request = exchange.getRequest().mutate().header("user", JSONObject.toJSONString(payloadDto)).build();
            exchange = exchange.mutate().request(request).build();
        } catch (Exception e) {
            //todo 多种失败异常判断
            log.error("token校验失败！",e);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body= JSONUtil.toJsonStr(CommonResult.failed("token校验失败！"));
            DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }
}
