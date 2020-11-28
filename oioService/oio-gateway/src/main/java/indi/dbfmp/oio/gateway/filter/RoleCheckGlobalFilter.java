package indi.dbfmp.oio.gateway.filter;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import indi.dbfmp.web.common.dto.CommonResult;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.dto.RoleCheck;
import inid.dbfmp.oauth.api.service.DubboAuthService;
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
 *  角色检查过滤器
 * </p>
 *
 * @author dbfmp
 * @name: RoleCheckGlobalFilter
 * @since 2020/11/28 下午8:22
 */
@Slf4j
//过滤器优先级
@Order(1)
@Component
public class RoleCheckGlobalFilter implements GlobalFilter {

    @Reference(check = false,group = "${spring.profiles.active}")
    private DubboAuthService dubboAuthService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String pass = exchange.getRequest().getHeaders().getFirst("whiteUrl");
        if ("pass".equals(pass)) {
            return chain.filter(exchange);
        }
        PayloadDto payloadDto = JSONObject.parseObject(exchange.getRequest().getHeaders().getFirst("user"),PayloadDto.class);
        if (null == payloadDto) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body= JSONUtil.toJsonStr(CommonResult.failed("非法操作！"));
            DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        if (!dubboAuthService.roleCheck(RoleCheck.builder().url(request.getURI().getPath()).userId(payloadDto.getUserId()).orgId(payloadDto.getOrgId()).build())) {
            //无权访问
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            String body= JSONUtil.toJsonStr(CommonResult.forbidden("没有权限访问哦～"));
            DataBuffer buffer =  response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }
}
