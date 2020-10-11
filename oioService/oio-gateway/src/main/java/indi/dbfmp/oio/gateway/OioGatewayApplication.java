package indi.dbfmp.oio.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * <p>
 *  服务启动类
 * </p>
 *
 * @author dbfmp
 * @name: OioGatewayApplication
 * @since 2020/10/11 2:21 下午
 */
@EnableDiscoveryClient
@SpringBootApplication
public class OioGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(OioGatewayApplication.class,args);
    }

}
