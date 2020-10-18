package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *  使用authCode，clientId，clientPwd获取token
 * </p>
 *
 * @author dbfmp
 * @name: AuthCodeTokenDto
 * @since 2020/10/11 7:36 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AuthCodeTokenDto implements Serializable {

    private static final long serialVersionUID = 525569924816469445L;

    private String authCode;

    private String clientId;

    private String clientPwd;

    /**
     * 应用类型
     */
    private String appType;

}
