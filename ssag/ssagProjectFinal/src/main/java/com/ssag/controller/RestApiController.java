package com.ssag.controller;

import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.UserDao;
import com.ssag.model.CompanyVo;
import com.ssag.model.UserVo;
import com.ssag.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
public class RestApiController {
	
	private final UserDao userDao;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal PrincipalDetails details) {
		String username = details.getUsername(); 
		return "index";
	}
	//일반회원가입 기업회원가입 갈라지는 경로
	@GetMapping("/whoareu")
	public String whoareu() {
		return"whoareu";
	}
	//기업회원 회원가입
	@GetMapping("/company-register")
	public String createCompany(Model model) {
		List<CompanyVo> companyList = userService.companyList();
		model.addAttribute("companyList", companyList);
		return "company-register";
	}
	//일반회원 회원가입
	@GetMapping("/user-register")
	public String createUser() {
		return "user-register";
	}

	@PostMapping("/company-register")
	public String companyRegister(UserVo userVo, CompanyVo companyVo) {
		// 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userVo.setRole("ROLE_COMPANY");
		userVo.setCompanycode(companyVo.getCompanycode());
		System.out.println("companyCode!! "+companyVo.getCompanycode());
		userService.addUser(userVo);
		System.out.println("USerVo22 =========================================" + userVo.getUsername());
		return "redirect:/";
	}
	
	@PostMapping("/user-register")
	public String userRegister(UserVo userVo) {
		// 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userVo.setRole("ROLE_USER");
		userService.addUser(userVo);
		System.out.println("USerVo22 =========================================" + userVo.getUsername());
		return "redirect:/";
	}


	@GetMapping("/login")
	public String login() {
		return "login";
	}

	//인증을 받은 사용자가 로그아웃가능  로그아웃은 SecurityContextLogoutHandler이친구가 진행함
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println("1111"+authentication.getDetails());
		System.out.println("2222"+authentication.getName());
		System.out.println("3333"+authentication.getPrincipal());
		//authentication 이 널이 아니면 로그아웃진행
		if(authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}

	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal PrincipalDetails userVo,Model model) throws Exception{
		System.out.println("Account============================"+userVo);
		UserVo userVo2 = userService.findById(userVo.getUsername());
		System.out.println("Mypage UserVo : " + userVo.getName());
		System.out.println("Mypage UserVo2 : " + userVo2.getName());
		model.addAttribute("account", userVo2);
		System.out.println("Model getId ======================"+ model);
		return "mypage";
	}

	@PostMapping("/mypage")
	public String updateUser(UserVo userVo,@AuthenticationPrincipal UserVo user) throws Exception {
		userVo.setUsercode(user.getUsercode());
		userService.updateUser(userVo);
		System.out.println("update userController UserVo : "+ userVo);
		System.out.println("update userController UserVo : "+ userVo.getUsercode());
		return "redirect:/";
	}

	// 예시 insert 프로시저 호출가능 음 필요한 테이블 더 보고 완성형으로 해보면 될듯!!
	@GetMapping("/call")
	public String callProcedure() {
		userService.procedureCall();
		return "redirect:/";
	}

	@PostMapping("/api/v1/user/token")
	public String token(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		System.out.println("Controller Header : " + header);
		return "token";
	}
	
	@GetMapping("/oauth2User")
	@ResponseBody
	public String oauth2User(@AuthenticationPrincipal UserVo principal, @AuthenticationPrincipal PrincipalDetails details) {
		System.out.println(details.getUsername());
//		System.out.println(principal.getUsername());
		
//		System.out.println(attributes.getUser());
//		System.out.println(attributes.getUsername());
//		System.out.println(attributes.getAttributes());
		return "구글 로그인 확인페이지";
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal);
		System.out.println("OAuth2 : "+principal.getUsername());
		System.out.println("OAuth2 : "+principal.getUser().getUsername());
		// iterator 순차 출력 해보기
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();
		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "유저 페이지입니다.";
	}
	
	// 아이디 찾기 폼
	@GetMapping(value = "/find_id")
	public String find_id_form() throws Exception{
		return "find_id_form";
	}
	
	@PostMapping(value = "/find_id")
	public String find_id(HttpServletResponse response, @RequestParam("email") String email, Model md) throws Exception{
		System.out.println("find_id :" + userService.find_id(response, email));
		md.addAttribute("id", userService.find_id(response, email));
		return "find_id";
	}
	
	// 비밀번호 찾기 폼
		@GetMapping(value = "/find_pw_form")
		public String find_pw_form() throws Exception{
			return "find_pw_form";
		}
	
		
		// 비밀번호 찾기
		@PostMapping(value = "/find_pw")
		public void find_pw(@ModelAttribute UserVo userVo, HttpServletResponse response) throws Exception{
			System.out.println(" 컨트롤에서 받는 아이디 : "+ userVo.getUsername());
			userService.find_pw(response, userVo);
		}
		
		@GetMapping("/pw-change")
		public String pwChange() {
			return "pw-change";
		}
		
		@PostMapping("/pw-change")
		public String checkPw(@RequestParam("password") String password, HttpSession session, @AuthenticationPrincipal PrincipalDetails details) {
			log.info("비밀번호 확인 요청 발생");
			String result = null;
			if(!passwordEncoder.matches(password, details.getPassword())) {
				result = "pwConfirmOK";
			}else {
				result = "pwConfirmNO";
			}
			return result;
		}
		
		
}