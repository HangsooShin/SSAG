//package com.ssag.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.ssag.jwt.JwtAuthenticationFilter;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@Configuration
//public class FilterConfig {
//
//	@Bean
//	public FilterRegistrationBean<MyFilter3> filter1() {
//		FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(0); // 낮은 번호가 필터중에 가장먼저 실행됨
//		return bean;
//	}
//
//	@Bean
//	public FilterRegistrationBean<MyFilter2> filter2() {
//		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
//		bean.addUrlPatterns("/*");
//		bean.setOrder(1); // 낮은 번호가 필터중에 가장먼저 실행됨
//		return bean;
//	}
//
//	@Bean
//	public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
//		System.out.println("JwtAuthenticationFilter 필터 등록");
//		FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(new JwtAuthenticationFilter(personRepository));
//		bean.addUrlPatterns("/login");
//		bean.setOrder(1); // 낮은 번호부터 실행됨.
//		return bean;
//	}
//
//	@Bean
//	public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilter() {
//		System.out.println("JwtAuthorizationFilter 필터 등록");
//		FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
//				new JwtAuthorizationFilter(personRepository));
//		bean.addUrlPatterns("/post/*");
//		bean.setOrder(2); // 낮은 번호부터 실행됨.
//		return bean;
//	}
//
//}
