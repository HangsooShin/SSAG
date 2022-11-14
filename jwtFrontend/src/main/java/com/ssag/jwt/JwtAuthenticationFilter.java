package com.ssag.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssag.config.auth.PrincipalDetails;
import com.ssag.model.UserVo;

import lombok.RequiredArgsConstructor;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
//login 요청해서 Username password 전송하면(post)
//UsernamePasswordAuthenticationFilter 동작함
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;

//	@Autowired
//	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
//		this.authenticationManager = authenticationManager;
//
//	}

	// login 요청을 하면 로그인 시도를 위해서 실행되는 함수
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter : 로그인 시도중");
		// TODO Auto-generated method stub

		// 1. username, password 받아서

		ObjectMapper om = new ObjectMapper();
		UserVo loginRequestVo = null;
		try {
//			BufferedReader br = request.getReader();
//			String input = null;
//			while((input = br.readLine()) !=null) {
//				System.out.println(input);
//			}
//			System.out.println(request.getInputStream().toString());

			loginRequestVo = om.readValue(request.getInputStream(), UserVo.class);
			System.out.println(loginRequestVo);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("=========================================================================");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginRequestVo.getUsername(), loginRequestVo.getPassword());

		// authenticate() 함수가 호출 되면 인증 프로바이더가 유저 디테일 서비스의
		// loadUserByUsername(토큰의 첫번째 파라메터) 를 호출하고
		// UserDetails를 리턴받아서 토큰의 두번째 파라메터(credential)과
		// UserDetails(DB값)의 getPassword()함수로 비교해서 동일하면
		// Authentication 객체를 만들어서 필터체인으로 리턴해준다.

		// Tip: 인증 프로바이더의 디폴트 서비스는 UserDetailsService 타입
		// Tip: 인증 프로바이더의 디폴트 암호화 방식은 BCryptPasswordEncoder
		// 결론은 인증 프로바이더에게 알려줄 필요가 없음.
		// principalDetailsService의 loadUserByUsername() 함수가 실행 된 후 정상이면 authentication이
		// 리턴됨
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		// =>로그인이 되었다는 뜻
		PrincipalDetails principalDetailis = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Authentication : " + principalDetailis.getUserVo().getUsername());
		System.out.println("1==============================================");
		// Authentication 객체가 session영역에 저장됨
		return authentication;
	}

	// JWT Token 생성해서 response에 담아주기
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out.println("successfulAuthentication이 실행 됨 : 인증이 완료되었다는 뜻");
		PrincipalDetails principalDetailis = (PrincipalDetails) authResult.getPrincipal();

		//RSA방식은 아니고 Hash암호방식
		String jwtToken = JWT.create()
				.withSubject("cos토큰")
				.withExpiresAt(new Date(System.currentTimeMillis()+(6000*10))) //1분 *10
				.withClaim("code", principalDetailis.getUserVo().getCode())
				.withClaim("username", principalDetailis.getUserVo().getUsername())
				.sign(Algorithm.HMAC512("cos"));

		response.addHeader("Authorization", "Bearer "+jwtToken);

	}
	// 2. 정상인지 로그인 시도를 해보는 거에요 authenticationManager로 로그인 시도를 하면 !!!1
	// PrincipalDetailsService 가 호출이 된다. -> loadUserByUsername 이 실행된다.
	// 3.Principal Details를 세션에 담고 (이걸 안담으면 권한 관리가 안됨)
	// 4. JWT토큰을 만들어서 응답해주면 됨
}
