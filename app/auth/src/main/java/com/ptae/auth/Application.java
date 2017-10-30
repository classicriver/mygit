package com.ptae.auth;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


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
@SpringBootApplication(scanBasePackages = {"com.ptae.**"})
@EnableDiscoveryClient
public class Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);  	
    }	
}
