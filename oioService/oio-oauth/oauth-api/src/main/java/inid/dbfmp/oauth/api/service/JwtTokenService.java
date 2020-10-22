package inid.dbfmp.oauth.api.service;

import inid.dbfmp.oauth.api.dto.PayloadDto;
import inid.dbfmp.oauth.api.dto.RefreshToken;
import inid.dbfmp.oauth.api.dto.Token;
import inid.dbfmp.oauth.api.dto.VerifyTokenDto;

/**
 * <p>
 *  jwt服务
 * </p>
 *
 * @author dbfmp
 * @name: JwtTokenService
 * @since 2020/10/11 4:30 下午
 */
public interface JwtTokenService {

    VerifyTokenDto verifyToken(String token);

    Token refreshToken(RefreshToken refreshToken);
}
