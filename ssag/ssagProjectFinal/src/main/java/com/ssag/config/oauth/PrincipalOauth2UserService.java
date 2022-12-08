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
	private final UserService userService;
	private final UserDao userDao;
	public PrincipalOauth2UserService(PasswordEncoder passwordEncoder,UserDao userDao,UserService userService) {
		this.passwordEncoder=passwordEncoder;
		this.userDao=userDao;
		this.userService=userService;
	}
	
	//구글로 받은 UserRequest 데이터에 대한 후처리되는 함수
	// userRequest 는 code를 받아서 accessToken을 응답 받은 객체
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest); // google의 회원 프로필 조회

		// code를 통해 구성한 정보
		System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
		// token을 통해 응답받은 회원정보
		System.out.println("oAuth2User : " + oAuth2User);
	
		return processOAuth2User(userRequest, oAuth2User);
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

		// Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
		OAuth2UserInfo oAuth2UserInfo = null;
		if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
			System.out.println("구글 로그인 요청~~");
			oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
//		} else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
//			System.out.println("페이스북 로그인 요청~~");
//			oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
//		} else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")){
//			System.out.println("네이버 로그인 요청~~");
//			oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
		} else {
			System.out.println("우리는 구글과 페이스북만 지원해요 ㅎㅎ");
		}

		oAuth2User = super.loadUser(userRequest);
		String provider = userRequest.getClientRegistration().getClientId();//google
		String providerid = oAuth2User.getAttribute("sub");
		String username = provider +"_" + providerid; //google 고유아이디 생성
		String password = passwordEncoder.encode("겟인데어");
		String email = oAuth2User.getAttribute("email");
		String role = "ROLE_USER";
		String name = oAuth2User.getAttribute("name");
		//System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
		//System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
//		Optional<UserVo> userOptional = 
//				userService.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
		
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
			System.out.println("test1 ; " +user);
			System.out.println("test1 ; " + oAuth2User.getAttributes());
		}
		
		
		return new PrincipalDetails(user, oAuth2User.getAttributes());
	}
}