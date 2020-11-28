package indi.dbfmp.oio.oauth.core.service.dubboServiceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import indi.dbfmp.oio.oauth.core.dto.webDto.RefreshDto;
import indi.dbfmp.oio.oauth.core.dto.webDto.TokenDto;
import inid.dbfmp.oauth.api.exception.CommonException;
import indi.dbfmp.oio.oauth.core.service.impl.TokenService;
import inid.dbfmp.oauth.api.dto.RefreshToken;
import inid.dbfmp.oauth.api.dto.Token;
import inid.dbfmp.oauth.api.dto.VerifyTokenDto;
import inid.dbfmp.oauth.api.service.JwtTokenService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private TokenService tokenService;

    @Override
    public VerifyTokenDto verifyToken(String token) {
        if (StrUtil.isBlank(token)) {
            throw new CommonException("token不能为空");
        }
        return tokenService.verifyToken(token);
    }

    @Override
    public Token refreshToken(RefreshToken refreshToken) {
        RefreshDto refreshDto = RefreshDto.builder()
                .appType(refreshToken.getAppType())
                .clientId(refreshToken.getClientId())
                .clientPwd(refreshToken.getClientPwd())
                .refreshToken(refreshToken.getRefreshToken())
                .build();
        TokenDto tokenDto = tokenService.refreshToken(refreshDto);
        Token token = new Token();
        BeanUtil.copyProperties(tokenDto,token);
        return token;
    }
}
