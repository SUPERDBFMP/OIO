package indi.dbfmp.oio.oauth.core.service.impl;

import com.nimbusds.jose.JOSEException;
import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.service.JwtTokenService;
import inid.dbfmp.oauth.api.utils.JwtTokenUtil;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.text.ParseException;

/**
 * <p>
 *  jwtToken实现类
 * </p>
 *
 * @author dbfmp
 * @name: JwtTokenServiceImpl
 * @since 2020/10/11 4:49 下午
 */
@Component
@Service(group = "${spring.profiles.active}")
public class JwtTokenServiceImpl implements JwtTokenService {

    @Value("${jwtRsaKeyPath}")
    private String jwtRsaKeyPath;
    @Value("${jwtRsaKey}")
    private String jwtRsaKey;

    @Override
    public PayloadDto verifyAndGetPayloadDto(String token) throws MalformedURLException, ParseException, JOSEException {
        //todo 数据库校验
        return JwtTokenUtil.verifyTokenByRSA(token,JwtTokenUtil.getDefaultRSAKey(jwtRsaKeyPath,jwtRsaKey));
    }
}
