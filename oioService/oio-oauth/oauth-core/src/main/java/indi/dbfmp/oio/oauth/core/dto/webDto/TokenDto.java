package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *  返回web的token
 * </p>
 *
 * @author dbfmp
 * @name: TokenDto
 * @since 2020/10/11 7:33 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class TokenDto implements Serializable {

    private static final long serialVersionUID = 51782037739371964L;
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
