package indi.dbfmp.oio.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 *
 * </p>
 *
 * @author dbfmp
 * @name: OioAuthMain
 * @since 2020/6/15 8:48 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OioAuthMain {

    public static void main(String[] args) {
        SpringApplication.run(OioAuthMain.class,args);
    }

}
