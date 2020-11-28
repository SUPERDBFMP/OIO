package inid.dbfmp.oauth.api.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  jwt公共负载
 * </p>
 *
 * @author dbfmp
 * @name: PayloadDto
 * @since 2020/10/11 3:56 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class PayloadDto implements Serializable {

    private static final long serialVersionUID = -8821837671194520828L;

    private String sub;
    private Long iat;
    private Long exp;
    private String jti;
    private String userName;
    private String userId;
    private String appType;
    private String clientId;
    private String otherTokenId;
    private String orgId;

}
