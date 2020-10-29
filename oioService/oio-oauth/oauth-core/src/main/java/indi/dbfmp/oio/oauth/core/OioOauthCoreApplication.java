package indi.dbfmp.oio.oauth.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioOauthCoreApplication
 * @since 2020/10/11 4:48 下午
 */
@EnableScheduling
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("indi.dbfmp.oio.oauth.core.mapper")
public class OioOauthCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(OioOauthCoreApplication.class,args);
    }

}
