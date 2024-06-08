package com.fpoly.sd18306.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fpoly.sd18306.components.AuthInterceptor;



@Configuration
public class InterceptorConfig implements WebMvcConfigurer{
	@Autowired
	AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)
		.excludePathPatterns("/login","/images/**","/index","/user/index","/register","/error/**");
	}
}
