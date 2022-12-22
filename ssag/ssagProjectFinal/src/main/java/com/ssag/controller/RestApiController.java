package com.ssag.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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
//		String username = details.getUsername();
		return "index";
	}

	// 일반회원가입 기업회원가입 갈라지는 경로
	@GetMapping("/whoareu")
	public String whoareu() {
		return "whoareu";
	}

	// 기업회원 회원가입
	@GetMapping("/company-register")
	public String createCompany(Model model) {
		List<CompanyVo> companyList = userService.companyList();
		model.addAttribute("companyList", companyList);
		return "company-register";
	}

	// 일반회원 회원가입
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
		log.info("companyCode!! " + companyVo.getCompanycode());
		userService.addUser(userVo);
		return "redirect:/";
	}

	@PostMapping("/user-register")
	public String userRegister(UserVo userVo) {
		// 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userVo.setRole("ROLE_USER");
		userService.addUser(userVo);
		log.info("username : " + userVo.getUsername());
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// 인증을 받은 사용자가 로그아웃가능 로그아웃은 SecurityContextLogoutHandler이친구가 진행함
	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// authentication 이 널이 아니면 로그아웃진행
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}

	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal PrincipalDetails details, Model model) throws Exception {
		UserVo userlist = userService.findById(details.getUsername());
		model.addAttribute("account", userlist);
		return "mypage";
	}

	// mypage 수정요청시 업데이트 user
	@PostMapping("/mypage")
	public String updateUser(UserVo userVo,@AuthenticationPrincipal PrincipalDetails user,Model model) throws Exception {
		userVo.setUsercode(user.getUser().getUsercode());
		System.out.println("update service 탄 전");
		userService.updateUser(userVo);
		System.out.println("update service 탄 후");
		return "redirect:/";
	}

	// 아이디 찾기 폼
	@GetMapping(value = "/find_id")
	public String find_id_form() throws Exception {
		return "find_id_form";
	}

	@PostMapping(value = "/find_id")
	public String find_id(HttpServletResponse response, @RequestParam("email") String email, Model md)
			throws Exception {
		System.out.println("find_id :" + userService.find_id(response, email));
		md.addAttribute("id", userService.find_id(response, email));
		return "find_id";
	}

	// 비밀번호 찾기 폼
	@GetMapping(value = "/find_pw_form")
	public String find_pw_form() throws Exception {
		return "find_pw_form";
	}

	// 비밀번호 찾기
	@PostMapping(value = "/find_pw")
	public void find_pw(@ModelAttribute UserVo userVo, HttpServletResponse response) throws Exception {
		System.out.println(" 컨트롤에서 받는 아이디 : " + userVo.getUsername());
		userService.find_pw(response, userVo);
	}

	//비밀번호 변경
	@GetMapping("/pw-change")
	public String pwChange() {
		return "pw-change";
	}

	@PostMapping("/pw-change")
	public String checkPw(@RequestParam("password") String password, HttpSession session,
			@AuthenticationPrincipal PrincipalDetails details) {
		log.info("비밀번호 확인 요청 발생");
		String result = null;
		if (!passwordEncoder.matches(password, details.getPassword())) {
			result = "pwConfirmOK";
		} else {
			result = "pwConfirmNO";
		}
		return result;
	}
	
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("Principal : " + principal);
		System.out.println("OAuth2 : "+principal.getUsername());
		System.out.println("OAuth2 : "+principal.getUser().getName());
		System.out.println("OAuth2 : "+principal.getUser().getUsername());
		// iterator 순차 출력 해보기
		

		return "유저 페이지입니다.";
	}
	
	@PostMapping("/usernameCheck")
	@ResponseBody
	public Integer usernameCheck(@RequestParam("username") String username, Model model) {
		model.addAttribute("username", username);
		Integer cnt = userService.count_id(username);
		return cnt;
	}
	
//	@InitBinder("passwordForm")
//	public void initBinder(WebDataBinder webDataBinder) {
//		webDataBinder.addValidators(new PasswordFormValidator);
//	}
	
//    @GetMapping(SETTINGS_PASSWORD_URL) // (3)
//    public String passUpdateForm(@CurrentUser Account account, Model model) {
//        model.addAttribute(account);
//        model.addAttribute(new PasswordForm());
//        return SETTINGS_PASSWORD_VIEW_NAME;
//    }
//
//    @PostMapping(SETTINGS_PASSWORD_URL) // (4) 
//    public String updatePassword(@CurrentUser Account account, @Valid PasswordForm passwordForm, Errors errors, Model model, RedirectAttributes attributes) {
//        if (errors.hasErrors()) {
//            model.addAttribute(account);
//            return SETTINGS_PASSWORD_VIEW_NAME;
//        }
//        accountService.updatePassword(account, passwordForm.getNewPassword()); // (5)
//        attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
//        return "redirect:" + SETTINGS_PASSWORD_URL;
//    }

}
