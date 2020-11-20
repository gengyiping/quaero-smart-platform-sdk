package com.quaero.quaerosmartplatform.config;


import com.quaero.quaerosmartplatform.domain.interceptor.ResponseResultInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ResponseResultInterceptor responseResultInterceptor() {
		return new ResponseResultInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//响应结果控制拦截
		registry.addInterceptor(responseResultInterceptor());
	}

}
