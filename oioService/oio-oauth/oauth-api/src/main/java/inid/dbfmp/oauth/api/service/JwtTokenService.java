package inid.dbfmp.oauth.api.service;

import inid.dbfmp.oauth.api.dto.PayloadDto;

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

    PayloadDto verifyAndGetPayloadDto(String token) throws Exception;

}
