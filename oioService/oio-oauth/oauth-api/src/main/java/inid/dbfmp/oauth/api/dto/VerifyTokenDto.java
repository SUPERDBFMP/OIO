package inid.dbfmp.oauth.api.dto;

import lombok.Builder;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: VerifyTokenDto
 * @since 2020/10/14 10:00 下午
 */
@Data
@Builder
public class VerifyTokenDto {

    private PayloadDto payloadDto;

    private String token;

}
