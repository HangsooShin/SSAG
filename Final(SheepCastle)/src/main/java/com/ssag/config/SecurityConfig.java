//package com.ssag.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration // IoC 빈(bean)을 등록
//@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
////@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // 특정 주소 접근시 권한 및 인증을 위한 어노테이션 활성화
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//
//	@Bean
//	@Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//	
//	
//	
////	@Autowired
////	private PrincipalOauth2UserService principalOauth2UserService;
////
////	@Bean
////	public PasswordEncoder passwordEncoder() {
////		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
////	}
////
//////    @Bean
//////    public PasswordEncoder passwordEncoder(){
//////        return new BCryptPasswordEncoder();
//////    }
////
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		http.csrf().disable();
////		((HttpSecurity) http.authorizeRequests()
////				.antMatchers("/","/company-register","/user-register","/login","*/whoareu","/analysis","/searchresult","/find_id","/find_pw_form","/app/login","/testrecipesearch","/testmerchandiseresult","/testmerch","/app/**")
////				.permitAll()
////				.antMatchers("/css/**","/js/**","/images/**","/scss/**","/fonts/**","/mail/**").permitAll() // 이부분
////				.antMatchers("/mypage").hasAnyRole("USER","COMPANY")
////				.antMatchers("/fridgeBox","/myfridge","/procedureList","/recipeList","/myFridgeBoxList").hasRole("USER")
////				.anyRequest().authenticated()
////				.and()
////				.formLogin()
////				.loginPage("/login")
////				.loginProcessingUrl("/login_proc")
////				.defaultSuccessUrl("/")
////				.and())
////				.oauth2Login()
////				.loginPage("/login") //구글 로그인 이후의 처리과정 필요(코드받기(인증) -> 엑세스토큰(권한) 사용자프로필가져오고 -> 그정보 토대로 가입 또는 추가 정보받기)
////				//Tip. 구글 로그인 하면 코드받는게 아니라 엑세스토큰 + 사용자프로필 정보 받는다.
////				.userInfoEndpoint()
////				.userService(principalOauth2UserService);
////
////	}
//
//
//}
