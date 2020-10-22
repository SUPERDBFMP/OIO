package inid.dbfmp.oauth.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: VerifyTokenDto
 * @since 2020/10/14 10:00 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyTokenDto implements Serializable {

    private static final long serialVersionUID = 7143574577901010359L;

    private PayloadDto payloadDto;

    private String token;

}
