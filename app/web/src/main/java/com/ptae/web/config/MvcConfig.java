package com.ptae.web.config;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ptae.auth.interceptor.AuthInterceptor;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy(proxyTargetClass = true) // cglib代理配置在Springmvc中,这样才可以代理Controller
@ComponentScan(basePackages = "com.ptae.web.**.controller")
public class MvcConfig extends WebMvcConfigurationSupport {
	/**
	 * 描述 : <注册试图处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 *//*
	@Bean
	public ViewResolver viewResolver() {
		// UrlBasedViewResolver vr = new UrlBasedViewResolver();
		// TilesView tv = new TilesView();
		// vr.setViewClass(TilesView.class);
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("WEB-INF/html/");
		viewResolver.setSuffix(".html");
		return viewResolver;
	}*/

	/**
	 * 描述 : <注册消息资源处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		return messageSource;
	}

	/**
	 * 描述 : <注册servlet适配器>. <br>
	 * <p>
	 * <只需要在自定义的servlet上用@Controller("映射路径")标注即可>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public HandlerAdapter servletHandlerAdapter() {
		return new SimpleServletHandlerAdapter();
	}

	/**
	 * 描述 : <本地化拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	/**
	 * 描述 : <基于cookie的本地化资源处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean(name = "localeResolver")
	public CookieLocaleResolver cookieLocaleResolver() {
		return new CookieLocaleResolver();
	}

	/**
	 * 描述 : <注册自定义拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	/*
	 * @Bean public CP_InitializingInterceptor initializingInterceptor(){
	 * logger.info("CP_InitializingInterceptor"); return new
	 * CP_InitializingInterceptor(); }
	 */

	/**
	 * 描述 : <RequestMappingHandlerMapping需要显示声明，否则不能注册自定义的拦截器>. <br>
	 * 
	 * @return
	 */
	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping() {
		return super.requestMappingHandlerMapping();
	}
	/**
	 * 
	 * @return
	 * @Description: TODO token拦截器
	 */
	@Bean
	public AuthInterceptor getAuthInterceptor(){
		return new AuthInterceptor();
		
	}
	/**
	 * 描述 : <添加拦截器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param registry
	 */
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		registry.addInterceptor(localeChangeInterceptor());
		// 注册token验证拦截器
		registry.addInterceptor(getAuthInterceptor())
									.addPathPatterns("/adas-app/**")
									.excludePathPatterns("/adas-app/sentAuthCode/**")
									.excludePathPatterns("/adas-app/login/**")
									.excludePathPatterns("/adas-app/protocol/**")
									.excludePathPatterns("/adas-app/isLogined/**");
	}

	/**
	 * 描述 : <HandlerMapping需要显示声明，否则不能注册资源访问处理器>. <br>
	 * 
	 * @return
	 */
	@Bean
	public HandlerMapping resourceHandlerMapping() {
		return super.resourceHandlerMapping();
	}

	/**
	 * 描述 : <资源访问处理器>. <br>
	 * <p>
	 * <可以在jsp中使用resource/**的方式访问/resource/下的内容>
	 * </p>
	 * 
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 这里和web不一样，路径前必须加classpath
		registry.addResourceHandler("WEB-INF/**").addResourceLocations("classpath:WEB-INF/");
		registry.addResourceHandler("upload/images/**").addResourceLocations("classpath:upload/images/");
	}

	/**
	 * 描述 : <文件上传处理器>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver commonsMultipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * 描述 : <异常处理器>. <br>
	 * <p>
	 * <系统运行时遇到指定的异常将会跳转到指定的页面>
	 * </p>
	 * 
	 * @return
	 */
	/*
	 * @Bean(name="exceptionResolver") public CP_SimpleMappingExceptionResolver
	 * simpleMappingExceptionResolver(){
	 * logger.info("CP_SimpleMappingExceptionResolver");
	 * CP_SimpleMappingExceptionResolver simpleMappingExceptionResolver= new
	 * CP_SimpleMappingExceptionResolver();
	 * simpleMappingExceptionResolver.setDefaultErrorView("common_error");
	 * simpleMappingExceptionResolver.setExceptionAttribute("exception");
	 * Properties properties = new Properties();
	 * properties.setProperty("java.lang.RuntimeException", "common_error");
	 * simpleMappingExceptionResolver.setExceptionMappings(properties); return
	 * simpleMappingExceptionResolver; }
	 */

	/**
	 * 描述 : <RequestMappingHandlerAdapter需要显示声明，否则不能注册通用属性编辑器>. <br>
	 * 
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter requestMappingHandlerAdapter() {
		return super.requestMappingHandlerAdapter();
	}

	/**
	 * 描述 : <注册通用属性编辑器>. <br>
	 * <p>
	 * <这里只增加了字符串转日期和字符串两边去空格的处理>
	 * </p>
	 * 
	 * @return
	 */
	/*
	 * @Override protected ConfigurableWebBindingInitializer
	 * getConfigurableWebBindingInitializer() {
	 * logger.info("ConfigurableWebBindingInitializer");
	 * ConfigurableWebBindingInitializer initializer =
	 * super.getConfigurableWebBindingInitializer(); CP_PropertyEditorRegistrar
	 * register = new CP_PropertyEditorRegistrar();
	 * register.setFormat("yyyy-MM-dd");
	 * initializer.setPropertyEditorRegistrar(register); return initializer; }
	 */

	/**
	 * 解决json中文字符串乱码问题 --继承了默认配置
	 * 
	 * @param converters
	 */

	@Override
	protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
		MediaType mediaType = new MediaType("text", "html", Charset.forName("UTF-8"));
		List<MediaType> mediaTypes = new ArrayList<>();
		mediaTypes.add(mediaType);
		stringHttpMessageConverter.setSupportedMediaTypes(mediaTypes);

		// fastJson
		FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
		List<MediaType> mediaTypes_2 = new ArrayList<>();
		MediaType mediaType_2 = new MediaType("application", "json", Charset.forName("UTF-8"));
		mediaTypes_2.add(mediaType);
		mediaTypes_2.add(mediaType_2);
		jsonConverter.setSupportedMediaTypes(mediaTypes_2);

		converters.add(2, stringHttpMessageConverter);// 添加的自定义的stringHttpMessageConverter
		converters.add(jsonConverter);
	}

}
