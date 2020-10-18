package indi.dbfmp.oio.oauth.core.dto.webDto;

import lombok.*;

import java.io.Serializable;

/**
 * <p>
 *  刷新tokenDto
 * </p>
 *
 * @author dbfmp
 * @name: RefreshDto
 * @since 2020/10/13 9:57 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Builder
public class RefreshDto implements Serializable {

    private static final long serialVersionUID = -1825150494862198933L;

    private String clientId;

    private String clientPwd;

    /**
     * 应用类型
     */
    private String appType;

    private String refreshToken;

}
