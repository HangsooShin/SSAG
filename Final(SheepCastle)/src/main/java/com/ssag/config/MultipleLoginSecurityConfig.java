package com.ssag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.ssag.config.oauth.PrincipalOauth2UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity
public class MultipleLoginSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}


	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter{

		@Autowired
		private PrincipalOauth2UserService principalOauth2UserService;

		@Autowired
		private AuthenticationFailureHandler customFailureHandler;



		@Bean
		public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
			http.csrf().disable();
			http.antMatcher("/app/**").authorizeRequests()
					.antMatchers("/app/whoareu", "/app/login", "/app", "/app/company-register", "/app/user-register",
							"/app/", "/app/find_id", "/app/find_pw", "/usernameCheck", "/app-header","/uploadimage","/upload",
							"/app/testmerchandiseresult", "/app/testmerch","/app/find_pw","/app/find_id","/app/pw_change")// 인증없이 접근가능한 페이지
					.permitAll()
					.antMatchers("/css/**", "/js/**", "/images/**", "/scss/**", "/fonts/**", "/mail/**", "/favicon.ico")
					.permitAll() // 이부분
					.antMatchers("/app/testfridge", "/app/testrecipesearch", "/searchresult", "/app/mypage",
							"/app/basket", "/app/deletecookbasket", "/app/updatecookbasket", "/app/checklist")
					.hasRole("USER").anyRequest().authenticated()
					// log in
					.and().formLogin().defaultSuccessUrl("/app").loginPage("/app/login")
					.loginProcessingUrl("/app/login_proc")
					
					.failureHandler(customFailureHandler) // 로그인 실패 핸들러
//	                .failureUrl("/loginAdmin?error=loginError")
					.and().logout().logoutUrl("/app/logout")
					.invalidateHttpSession(true)
					.logoutSuccessUrl("/app/login?logout").deleteCookies("JSESSIONID").permitAll()
					.and()
					.oauth2Login()
					.loginPage("/app/login")
					.userInfoEndpoint()
					.userService(principalOauth2UserService);
					
					
					
			return http.build();
		}

	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter {

		@Bean
		public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {
			http.csrf().disable();
			((HttpSecurity)http.antMatcher("/*").authorizeRequests()
					.antMatchers("/login", "/whoareu", "/user-register", "/company-register", "/searchresult", "/","/addArticle",
							"/usernameCheck","/removeArticle","/find_pw","/find_id","/pw_change","/oauth2/**")
					.permitAll()
					.antMatchers("/css/**", "/js/**", "/images/**", "/scss/**", "/fonts/**", "/mail/**", "/favicon.ico")
					.permitAll() // 이부분
					.antMatchers("/fridgeBox", "/myFridge", "/procedureList", "/recipeList", "/myFridgeBoxList")
					.hasRole("USER").anyRequest().authenticated()
					// log in
					.and().formLogin().defaultSuccessUrl("/").loginPage("/login").loginProcessingUrl("/login_proc")
//	                .failureUrl("/loginAdmin?error=loginError")
					.and())
					.oauth2Login()
					.loginPage("/login")
					.userInfoEndpoint()
					.userService(principalOauth2UserService);
//					.and().logout().logoutUrl("/logout").invalidateHttpSession(true).logoutSuccessUrl("/login?logout")
//					.permitAll();
				
			return http.build();
		}

		@Autowired
		private PrincipalOauth2UserService principalOauth2UserService;

	}
	

}