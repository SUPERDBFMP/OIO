package inid.dbfmp.oauth.api.dto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: Token
 * @since 2020/10/22 10:29 下午
 */
@EqualsAndHashCode
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {

    private static final long serialVersionUID = -2375606393087331950L;
    /**
     * 访问令牌
     */
    private String token;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 有效时间（秒）
     */
    private int expiresIn;

}
