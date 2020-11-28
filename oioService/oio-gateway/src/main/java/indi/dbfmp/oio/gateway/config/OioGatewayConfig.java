package indi.dbfmp.oio.gateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioGatewayConfig
 * @since 2020/11/28 下午7:50
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "oio.gateway")
public class OioGatewayConfig {

    private List<String> whiteUrlList;

}
