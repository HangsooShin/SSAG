package com.ssag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ssag.dao.UserDao;
import com.ssag.jwt.JwtAuthenticationFilter;
import com.ssag.jwt.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityCongfig extends WebSecurityConfigurerAdapter{

	
	private final CorsConfig corsConfig;
	private final UserDao userDao;
//	
//	@Autowired
//	public SecurityCongfig(CorsConfig corsConfig, UserDao userDao) {
//		this.corsConfig = corsConfig;
//		this.userDao = userDao;
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { 
//		http.addFilterBefore(new MyFilter3(),BasicAuthenticationFilter.class);
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session을 사용하지 않ㅇ겠다.
		.and()
		.addFilter(corsConfig.corsFilter()) //모든 요청은 다 이필터를 탐 
		.formLogin().disable()
		.httpBasic().disable() //basic 방법을 안쓰고 Bearer인증방식 사용할 것임
		.addFilter(new JwtAuthenticationFilter(authenticationManager())) //AuthenticaionManager 던져줘야 됨
		.addFilter(new JwtAuthorizationFilter(authenticationManager(),userDao))
		.authorizeRequests()
		.antMatchers("/api/v1/user/**")
		.access("hasRole('ROLE_USER')or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/manager/**")
		.access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/api/v1/admin/**")
		.access("hasRole('ROLE_ADMIN')")
		.anyRequest().permitAll();

	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(authenticationProvider());
//	
//	}
//	@Bean
//	public AuthenticationProvider authenticationProvider() {
//		return new CustomAuthenticationProvider();
//	}
}
