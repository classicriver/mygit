package com.ptae.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


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
@EnableEurekaServer
public class Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);  	
    }
    
	//war包启动入口
	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(Application.class);
	}

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {
		// TODO Auto-generated method stub
		servletContext.setInitParameter("webAppRootKey", "webframe.server");
		/*servletContext.setInitParameter("log4jConfigLocation",
				"classpath:log4j.properties");
		servletContext.addListener(Log4jConfigListener.class);*/
		super.onStartup(servletContext);
	}
	
}
