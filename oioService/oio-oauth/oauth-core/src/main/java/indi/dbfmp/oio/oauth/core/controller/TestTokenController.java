package indi.dbfmp.oio.oauth.core.controller;

import com.alibaba.fastjson.JSONObject;
import com.nimbusds.jose.JOSEException;
import inid.dbfmp.common.dto.CommonResult;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: TestTokenController
 * @since 2020/10/11 4:56 下午
 */
@Slf4j
@RestController
@RequestMapping("/testToken")
public class TestTokenController {

    @Value("${jwtRsaKeyPath}")
    private String jwtRsaKeyPath;
    @Value("${jwtRsaKey}")
    private String jwtRsaKey;

    @RequestMapping("/getToken")
    public CommonResult<?> getToken() {
        PayloadDto payloadDto = PayloadDto.builder()
                .appType("web")
                .userId("ht")
                .userName("dbfmp")
                .exp(System.currentTimeMillis()+50000)
                .build();
        try {
            return CommonResult.success(JwtTokenUtil.generateTokenByRSA(JSONObject.toJSONString(payloadDto),JwtTokenUtil.getDefaultRSAKey(jwtRsaKeyPath,jwtRsaKey)));
        } catch (JOSEException | MalformedURLException e) {
            log.error("生成token异常",e);
            return CommonResult.failed("生成token异常");
        }
    }

    @RequestMapping("/hello")
    public CommonResult<?> hello() {
        return CommonResult.success("hello");
    }

}
