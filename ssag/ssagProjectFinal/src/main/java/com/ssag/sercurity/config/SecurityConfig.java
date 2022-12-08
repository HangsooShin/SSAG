package com.ssag.sercurity.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssag.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsService userDetailsService;
	
	private AuthenticationDetailsSource authenticationDetailsSource;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		((HttpSecurity) http.authorizeRequests()
				.antMatchers("/","/company-register","/user-register","/login","/whoareu","/analysis","/searchresult")
				.permitAll()
				.antMatchers("/css/**","/js/**","/images/**","/scss/**","/fonts/**","/mail/**").permitAll() // 이부분
				.antMatchers("/mypage").hasAnyRole("USER","COMPANY")
				.antMatchers("/fridgeBox","/myfridge","/procedureList","/recipeList","/myFridgeBoxList").hasRole("USER")
				.anyRequest().authenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login_proc")
				.authenticationDetailsSource(authenticationDetailsSource)
				.defaultSuccessUrl("/")
				.and())
				.oauth2Login()
				.loginPage("/login") //구글 로그인 이후의 처리과정 필요(코드받기(인증) -> 엑세스토큰(권한) 사용자프로필가져오고 -> 그정보 토대로 가입 또는 추가 정보받기)
				//Tip. 구글 로그인 하면 코드받는게 아니라 엑세스토큰 + 사용자프로필 정보 받는다.
				.userInfoEndpoint()
				.userService(principalOauth2UserService);
				


	}
	

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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

//	 보안필더를 거치지 않고 static에 있는 프론트 코드 넘김 근데 requestMathchers에 빨간줄뜸
//	@Override
//	public void configure(WebSecurity web) throws Exception {
//		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//	}

	
	
	
}
