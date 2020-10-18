package indi.dbfmp.oio.oauth.core.dto.redisDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *  OauthCodeDto在redis中存储的对象
 * </p>
 *
 * @author dbfmp
 * @name: OauthCodeDto
 * @since 2020/10/12 9:59 下午
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OauthCodeDto implements Serializable {

    private static final long serialVersionUID = 1918300515382094916L;

    private String oauthCode;

    private String appType;

    private String clientId;

    private String userId;

    private String userNickName;

}
