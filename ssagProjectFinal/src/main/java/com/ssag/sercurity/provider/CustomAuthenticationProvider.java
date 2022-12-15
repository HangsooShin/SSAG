package com.ssag.sercurity.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ssag.sercurity.common.FormWebAuthenticationDetails;
import com.ssag.sercurity.service.AccountContext;


public class CustomAuthenticationProvider implements AuthenticationProvider{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
//	private final FormWebAuthenticationDetails formWebAuthenticationDetails;

//	public CustomAuthenticationProvider(FormWebAuthenticationDetails formWebAuthenticationDetails) {
//		this.formWebAuthenticationDetails = formWebAuthenticationDetails;
//	}
	
	//검증을 위한 구현
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	//인증에 대한 검즘
		String loginId =  authentication.getName();
		System.out.println("Authentication loginId : " + loginId);
		String password = (String)authentication.getCredentials();
		System.out.println("AUthenticaion loginPassword : "+password);
		AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(loginId);
		System.out.println("AccountContext password :  " + accountContext.getUserVo().getPassword());
		System.out.println("accountContext: ==============================: "+ accountContext);
		
		
		if(!passwordEncoder.matches(password, accountContext.getUserVo().getPassword())) { //사용자가 입력한 패스워드, DB에 저장된 비번
			System.err.println("비번 틀린데???????????????????????????????");
			throw new BadCredentialsException("BadCredentialsException");
			
		}
		
//		FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
	
//		String secretKey = formWebAuthenticationDetails.getSecretKey();
//		String secretKey = ((FormWebAuthenticationDetails) authentication.getDetails()).getSecretKey();
//		if(secretKey == null) {
//			throw new InsufficientAuthenticationException("InsufficientAuthenticationException Null");
//		}else if(!"secret".equals(secretKey)){
//			throw new InsufficientAuthenticationException("InsufficientAuthenticationException equals");
//		}
		
		//여기서 추가적인 검증을 진행할 수 있따.
		//비번 이외의
		//여기ㄱ까지오면 로그인 성공이다.																								                            //pasword = null
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(accountContext.getUserVo(),null, accountContext.getAuthorities());
		
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//토큰이 현재 파라매터로 전달될때 CustomAuthenticationProvider이 클래스가 인증을 처리하게만듬
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
//		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
