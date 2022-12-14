package com.ssag.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ssag.config.oauth.PrincipalOauth2UserService;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class MultipleLoginSecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	private final PrincipalOauth2UserService principalOauth2UserService;

	@Configuration
	@Order(1)
	public static class App1ConfigurationAdapter {

//		@Bean
//		public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
//			http.antMatcher("/app").authorizeRequests()
//					.antMatchers("/app/login", "/app/whoareu", "/app/user-register", "/app/company-register",
//							"/app/searchresult", "/app")
//					.permitAll().anyRequest().hasRole("USER")
//
//					// log in
//					.and().formLogin().defaultSuccessUrl("/app").loginPage("/app/login")
//					.loginProcessingUrl("/app/login_proc")
////	                .failureUrl("/loginAdmin?error=loginError")
//					.and().csrf().disable();
//
//			return http.build();
//		}
		@Autowired
		private PrincipalOauth2UserService principalOauth2UserService;

//		@Bean
//		public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
//			http.csrf().disable();
//			http.antMatcher("/app").authorizeRequests()
//					.antMatchers("/app", "/app/company-register", "/app/user-register", "/app/login", "/app/whoareu",
//							"/app/analysis", "/app/searchresult", "/app/find_id", "/app/find_pw_form",
//							"/testrecipesearch", "/app/testmerchandiseresult","/app/login")
//					.permitAll().antMatchers("/css/**", "/js/**", "/images/**", "/scss/**", "/fonts/**", "/mail/**")
//					.permitAll() // 이부분
//					.antMatchers("/app/mypage").hasAnyRole("USER", "COMPANY")
//					.antMatchers("/app/fridgeBox", "/app/myFridge", "/app/procedureList", "/app/recipeList",
//							"/app/myFridgeBoxList","app/testfridge")
//					.hasRole("USER").anyRequest().authenticated()
//					.and()
//					.formLogin()
//					.loginPage("/login")
//					.loginProcessingUrl("/app/login_proc").defaultSuccessUrl("/app").and().oauth2Login()
//					.loginPage("/login")
//					// Tip. 구글 로그인 하면 코드받는게 아니라 엑세스토큰 + 사용자프로필 정보 받는다.
//					.userInfoEndpoint().userService(principalOauth2UserService);
//			return http.build();
//
//		}
		
		
		@Bean
		public SecurityFilterChain filterChainApp1(HttpSecurity http) throws Exception {
			http.antMatcher("/app/**").authorizeRequests()
					.antMatchers("/login/whoareu","/app/login","/app")
					.permitAll().antMatchers("/app/testfridge","/myFridge","/procedureList","/recipeList","/myFridgeBoxList").hasRole("USER")
					.anyRequest().authenticated()
					// log in
					.and().formLogin().defaultSuccessUrl("/app").loginPage("/app/login").loginProcessingUrl("/app/login_proc")
//	                .failureUrl("/loginAdmin?error=loginError")
					.and().csrf().disable();
			return http.build();
		}
		/* static 관련설정은 무시 */
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/images/**","/scss/**","/fonts/**","/mail/**");
		}
	}

	@Configuration
	@Order(2)
	public static class App2ConfigurationAdapter {

		@Bean
		public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {
			http.antMatcher("/*").authorizeRequests()
					.antMatchers("/login", "/whoareu", "/user-register", "/company-register", "/searchresult","/")
					.permitAll().antMatchers("/fridgeBox","/myFridge","/procedureList","/recipeList","/myFridgeBoxList").hasRole("USER")
					.anyRequest().authenticated()
					// log in
					.and().formLogin().defaultSuccessUrl("/").loginPage("/login").loginProcessingUrl("/login_proc")
//	                .failureUrl("/loginAdmin?error=loginError")
					.and().csrf().disable();
			return http.build();
		}

		/* static 관련설정은 무시 */
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/css/**", "/js/**", "/images/**","/scss/**","/fonts/**","/mail/**");
		}
//		
//	}

		@Autowired
		private PrincipalOauth2UserService principalOauth2UserService;

//		@Bean
//		public SecurityFilterChain filterChainApp2(HttpSecurity http) throws Exception {
//			http.csrf().disable();
//			((HttpSecurity) http.authorizeRequests()
//					.antMatchers("/","/login**")
//					.permitAll().antMatchers("/css/**", "/js/**", "/images/**", "/scss/**", "/fonts/**", "/mail/**")
//					.permitAll() // 이부분
//					.antMatchers("/mypage").hasAnyRole("USER", "COMPANY")
//					.antMatchers("/fridgeBox", "/myfridge", "/procedureList", "/recipeList", "/myFridgeBoxList")
//					.hasRole("USER").anyRequest().authenticated().and().formLogin().loginPage("/login")
//					.loginProcessingUrl("/login_proc").defaultSuccessUrl("/").and()).oauth2Login().loginPage("/login")
//					// Tip. 구글 로그인 하면 코드받는게 아니라 엑세스토큰 + 사용자프로필 정보 받는다.
//					.userInfoEndpoint().userService(principalOauth2UserService);
//			return http.build();
//
//		}
	}
}