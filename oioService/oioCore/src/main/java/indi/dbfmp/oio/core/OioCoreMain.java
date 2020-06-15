package indi.dbfmp.oio.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @NAME: OioCoreMain
 * @author dbfmp
 * @DATE: 2020/6/14 8:56 下午
 **/
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("indi.dbfmp.oio.core")
public class OioCoreMain {

    public static void main(String[] args) {
        SpringApplication.run(OioCoreMain.class,args);
    }

}
