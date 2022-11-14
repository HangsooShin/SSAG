//package com.ssag.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.ssag.filter.MyFilter3;
//import com.ssag.filter.MyFilter2;
//
//@Configuration
//public class FilterConfig {
//
//	@Bean
//	public FilterRegistrationBean<MyFilter3>filter1(){
//		FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(0); //낮은 번호가 필터중에 가장먼저 실행됨
//		return bean;
//	}
//	
//	@Bean
//	public FilterRegistrationBean<MyFilter2>filter2(){
//		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(1); //낮은 번호가 필터중에 가장먼저 실행됨
//		return bean;
//	}
//	
//}
