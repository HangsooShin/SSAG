package com.ssag.config.oauth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.UserDao;
import com.ssag.model.UserVo;
import com.ssag.service.UserService;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	private final PasswordEncoder passwordEncoder;
	
	public PrincipalOauth2UserService(PasswordEncoder passwordEncoder,UserDao userDao,UserService userService) {
		this.passwordEncoder=passwordEncoder;
		this.userDao=userDao;
		this.userService=userService;
	}
	
	
	private final UserService userService;
	
	private final UserDao userDao;
	
	//구글로 받은 UserRequest 데이터에 대한 후처리되는 함수
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("UserRequest " + userRequest.getClientRegistration());
		System.out.println("UserRequest " + userRequest.getAccessToken());
		
		//우리가 구글 로그인버튼 클릭-> 구글로그인 창 -> 로그인 완료 -> code를 리턴 (oauth-client라이브러리) -> accessToken요청
		//userRequest 정보 -> loadUser함수 호출 -> 구글로 부터 회원프로필받아준다. 
		System.out.println("UserRequest " + super.loadUser(userRequest).getAttributes());
		// TODO Auto-generated method stub
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		String provider = userRequest.getClientRegistration().getClientId();//google
		String providerid = oAuth2User.getAttribute("sub");
		String username = provider +"_" + providerid; //google 고유아이디 생성
		String password = passwordEncoder.encode("겟인데어");
		String email = oAuth2User.getAttribute("email");
		String role = "ROLE_USER";
		String name = oAuth2User.getAttribute("name");
		
		UserVo user = userService.findById(username);
		if(user == null) {
			user = UserVo.builder()
					.username(username)
					.password(password)
					.email(email)
					.role(role)
					.name(name)
					.build();
			userDao.insertUser(user);
		}else {
			System.out.println("이미 가입한 유저입니다.");
		}
		
		return new PrincipalDetails(user,oAuth2User.getAttributes());
	}
}
