package indi.dbfmp.oio.oauth.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioOauthCoreApplication
 * @since 2020/10/11 4:48 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OioOauthCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OioOauthCoreApplication.class,args);
    }

}
