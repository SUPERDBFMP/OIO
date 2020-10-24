package indi.dbfmp.oio.oauth.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @NAME: 线程池配置
 * @AUTHOR dbfmp
 * @DATE: 2020-03-21 18:59
 **/
@Slf4j
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("updateMiddleTablePool")
    public Executor updateMiddleTablePool() {
        log.info("start updateMiddleTablePool");
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);//配置核心线程数
        executor.setMaxPoolSize(8);//配置最大核心线程数
        executor.setQueueCapacity(1000);//配置队列容量
        executor.setKeepAliveSeconds(60);//设置线程活跃时间
        executor.setThreadNamePrefix("updateMiddleTablePool-");//设置线程名
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //委托当前线程执行
        executor.initialize();
        return executor;
    }

}
