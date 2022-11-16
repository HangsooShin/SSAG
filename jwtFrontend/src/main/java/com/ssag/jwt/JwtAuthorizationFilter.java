package com.ssag.jwt;

import java.awt.Window;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.UserDao;
import com.ssag.model.UserVo;

//시큐리티가 filter가지고 있는데 그 중에 BasicAuthenticationFilter 라는 것이 있음
//권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게되어있음
//만약 권한이 인증이 필요한 주소가 아니면 이 필터를 안탄다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserDao userDao;

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDao userDao) {
		super(authenticationManager);
		this.userDao = userDao;
	}

	// 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게됨
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");
		String header = request.getHeader("Authorization");
		
		String header2 = response.getHeader("Authorization");
		System.out.println("header : " + header);
		System.out.println("header : " + header2);
		if (header == null || !header.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		System.out.println("header : " + header);
		String token = request.getHeader("Authorization").replace("Bearer ", "");

		// 토큰 검증 (이게 인증이기 때문에 AuthenticationManager도 필요 없음)
		// 내가 SecurityContext에 집적접근해서 세션을 만들때 자동으로 UserDetailsService에 있는
		// loadByUsername이 호출됨.
		String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(token).getClaim("username").asString();
		System.out.println("Username : " + username);
		if (username != null) {
			UserVo user = userDao.findByUsername(username);

			// 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
			// 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, // 나중에 컨트롤러에서 DI해서
																										// 쓸 때 사용하기 편함.
					null, // 패스워드는 모르니까 null 처리, 어차피 지금 인증하는게 아니니까!!
					principalDetails.getAuthorities());

			// 강제로 시큐리티의 세션에 접근하여 값 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("SecurityContextHolder" + SecurityContextHolder.getContext());
		}

		chain.doFilter(request, response);
	}
}
