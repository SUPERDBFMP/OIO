package inid.dbfmp.oauth.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: RefreshToken
 * @since 2020/10/22 10:27 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken implements Serializable {

    private static final long serialVersionUID = -442720041109945812L;

    private String clientId;

    private String clientPwd;

    /**
     * 应用类型
     */
    private String appType;

    private String refreshToken;

}
