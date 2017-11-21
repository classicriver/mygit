package com.ptae.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


/**
 * 
 * @ClassName: Application
 * @Description: TODO(springboot 入口函数类) 
 * @author: sc
 * @date: 2017年5月9日
 * 版本信息：v 1.0
 */
/*@SpringBootApplication等同于以下三个注解同时使用
* {@link @ComponentScan,@Configuration,@SpringApplicationConfiguration}*/
@SpringBootApplication(scanBasePackages = {"com.ptae.**"},exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
@EnableHystrixDashboard
public class Application{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);  	
    }
}
