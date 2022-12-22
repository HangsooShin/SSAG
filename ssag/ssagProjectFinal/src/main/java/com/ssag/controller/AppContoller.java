package com.ssag.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.model.CompanyVo;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookbasketListVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.UserVo;
import com.ssag.service.FridgeService;
import com.ssag.service.IngredientService;
import com.ssag.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/app")
public class AppContoller {
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final IngredientService ingredientService;
	private final FridgeService fridgeService;

//	@Autowired
//	private MultipleLoginSecurityConfig multipleLoginSecurityConfig;

	List<SimilarnameVo> similarnameList = new ArrayList<SimilarnameVo>();
	List<FridgeBoxVo> fridgeBoxList = new ArrayList<FridgeBoxVo>();

	@GetMapping({ "/", "", "/app/login" })
	public String appIndex(@AuthenticationPrincipal PrincipalDetails details, Model model) {
		if (details != null) {
			model.addAttribute("userInfo", details.getUser());
		}
		return "test2";
	}

	// 일반회원가입 기업회원가입 갈라지는 경로
	@GetMapping("/whoareu")
	public String Appwhoareu() {
		return "appwhoareu";
	}

	// 기업회원 회원가입
	@GetMapping("/company-register")
	public String createCompany(Model model) {
		List<CompanyVo> companyList = userService.companyList();
		model.addAttribute("companyList", companyList);
		return "app-company-register";
	}

	// 일반회원 회원가입
	@GetMapping("/user-register")
	public String createUser() {
		return "app-user-register";
	}

	@PostMapping("/company-register")
	public String companyRegister(UserVo userVo, CompanyVo companyVo) {
		// 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userVo.setRole("ROLE_COMPANY");
		userVo.setCompanycode(companyVo.getCompanycode());
		log.info("companyCode!! " + companyVo.getCompanycode());
		userService.addUser(userVo);
		return "redirect:/app";
	}

	@PostMapping("/user-register")
	public String userRegister(UserVo userVo) {
		// 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
		userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
		userVo.setRole("ROLE_USER");
		userService.addUser(userVo);
		log.info("username : " + userVo.getUsername());
		return "redirect:/app";
	}

	@GetMapping("/login")
	public String applogin(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "exception", required = false) String exception, Model model) {
		System.out.println("로그인 페이지");
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "app-login";
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

	// 아이디 찾기 폼
	@GetMapping(value = "/find_id")
	public String find_id_form() throws Exception {
		System.out.println("아이디 찾기 페이지 진입");
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
	@GetMapping(value = "/find_pw")
	public String find_pw_form() throws Exception {
		return "find_pw_form";
	}

	// 비밀번호 찾기
	@PostMapping(value = "/find_pw")
	public void find_pw(@ModelAttribute UserVo userVo, HttpServletResponse response) throws Exception {
		System.out.println(" 컨트롤에서 받는 아이디 : " + userVo.getUsername());
		userService.find_pw(response, userVo);
	}

	// 비밀번호 변경
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

	@GetMapping("/testfridge")
	public String testFridge(Model model, @AuthenticationPrincipal PrincipalDetails user) {
		// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		FridgeVo fridgeVo = fridgeService.addFridge(user); // fridgecode 검증
//		FridgeVo fridgeList = fridgeService.userfridge(fridgeVo.getFridgecode());
//		System.out.println("FridgeVO.getCode11 : " + fridgeList.getFridgecode());

		// *********************************************** 냉장고 재료보여줌
		String userFridgeCode = userService.findById(user.getUser().getUsername()).getFridgecode();
		System.out.println("과연 변경 후 fridgecode 인가");
		fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
		model.addAttribute("fridgeBoxList", fridgeBoxList);

		// *********************************************** 재료 리스트 보여줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);

		// ---------------------------------------------------------------
		if (user != null) {
			model.addAttribute("userInfo", user.getUser());
		}
		return "testfridge";
	}
	
	// 앱 - 튀김 >> 튀김레시피 검색데이터페이지
		@PostMapping("/recipetypename")
		@ResponseBody
		public List<CookIngredientListVo> procedure4(SimilarnameVo similarnameVo, String similar, Model model) {
			System.out.println("=============== 검색어로 입력된 데이터 ================"+similarnameVo.getSimilar());
			List<CookIngredientListVo> list = ingredientService.joinDic2(similarnameVo.getSimilar());
			System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
			return list;
		}
		
		// 엡 - 감자 양파 >> 짜장밥 검색데이터페이지
		@PostMapping("/recipetypeingredient")
		@ResponseBody
		public List<CookIngredientListVo> procedure3(SimilarnameVo similarnameVo, String similar, Model model) {
			System.out.println("=============== 검색어로 입력된 데이터 ================"+similarnameVo.getSimilar());
			List<CookIngredientListVo> list = ingredientService.joinDic(similarnameVo.getSimilar());
//			System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
			return list;
		}
		

	// app index 감자 검색 데이터 페이지(데이터 쌓이는 페이지)
	@PostMapping(value = "/testmerch")
	@ResponseBody
	public List<SimilarnameVo> searchIngrdient(SimilarnameVo similarnameVo, Model model, String similar) {
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);

		return similarnameList;
	}

	// app index 감자 검색
	@PostMapping("/testmerchandiseresult")
	public String searchresulttest(String similar, Model model, @AuthenticationPrincipal PrincipalDetails user) {
		System.out.println("testmerchandiseresult 진입");
		model.addAttribute("similar", similar);
		if (user != null) {
			model.addAttribute("userInfo", user.getUser());
		}
		return "testmerchandiseresult";
	}

	@GetMapping("/testrecipesearch")
	public String testrecipesearch(@AuthenticationPrincipal PrincipalDetails user, Model model) {
		if (user != null) {
			model.addAttribute("userInfo", user.getUser());
		}
		return "testrecipesearch";
	}

	@PostMapping("/getcookbasket")
	@ResponseBody
	public void cookbasketVo (Integer cookcode, Integer cookquantityinbasket, @AuthenticationPrincipal PrincipalDetails details) {
		System.out.println("=============== 장바구니 추가 ===============");
		System.out.println(cookcode +" "+ cookquantityinbasket +" "+ details.getUser().getUsercode());
		ingredientService.updatecookcode(cookquantityinbasket, details.getUser().getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
	}

	@GetMapping("/basket")
	public String cookbasketpage(@AuthenticationPrincipal PrincipalDetails details, Model model) {
		System.out.println("=============== 장바구니 확인 =============== + usercode : " + details.getUser().getUsercode());
		List<CookbasketListVo> basketlist2 = ingredientService.cookbasket(details.getUser().getUsercode(),
				details.getUser().getFridgecode());
		log.info("basketList2" + basketlist2);

		try {
			List<CookbasketListVo> basketlist = ingredientService.cookbasket(details.getUser().getUsercode(),
					details.getUser().getFridgecode());
			System.out.println("데이터 받아옴");
			model.addAttribute("basketlist", basketlist);
			System.out.println("애드어트리뷰트까지 다함" + basketlist.get(0).getIngredientVoList());

		} catch (Exception e) {
			System.out.println("=============== 에러발생 ===============basket");
		}

		if (details != null) {
			model.addAttribute("userInfo", details.getUser());
		}
		return "basket";
	}

	// AppfridgeBox
	@PostMapping("/addfridgebox")
	public String createFridgeBox2(String ingredientcode, String ingredientquantityinfridgebox, String storagecode,
			String expiredate, FridgeBoxVo fridgeBoxVo, @AuthenticationPrincipal PrincipalDetails details) {

		String[] storagecodelist = storagecode.split(",");
		String[] ingredientcodelist = ingredientcode.split(",");
		String[] ingredientquantityinfridgeboxlist = ingredientquantityinfridgebox.split(",");
		String[] expiredatelist = expiredate.split(",");

		fridgeBoxVo = new FridgeBoxVo();

		for (int i = 0; i < storagecodelist.length; i++) {
			fridgeBoxVo.setStoragecode(Integer.parseInt(storagecodelist[i]));
			fridgeBoxVo.setIngredientcode(Integer.parseInt(ingredientcodelist[i]));
			fridgeBoxVo.setIngredientquantityinfridgebox(Integer.parseInt(ingredientquantityinfridgeboxlist[i]));
			fridgeBoxVo.setExpiredate(expiredatelist[i]);
			fridgeBoxVo.setFridgecode(details.getUser().getFridgecode());

			System.out.println("=============== 설정된 FridgeCode ===================" + details.getUser().getFridgecode());
			System.out.println("=============== 설정된 Ingredientcode ===============" + fridgeBoxVo.getIngredientcode());

			fridgeService.createFridgeBox(fridgeBoxVo);
		}
		return "redirect:/app/testfridge";
	}

	// AppFridgeBox 변경
	@PostMapping("/appchangefridgebox")
	public String changeFridgeBox3(FridgeBoxVo originalfridgeBoxVo, FridgeBoxVo fridgeBoxVo,
			String ingredientcreateddate, String expiredate, Integer ingredientcode, Integer storagecode,
			Integer ingredientquantityinfridgebox, @AuthenticationPrincipal PrincipalDetails userVo) {
		// DB에서 선택할 PK 기준
		originalfridgeBoxVo.setIngredientcode(ingredientcode);
		originalfridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
		originalfridgeBoxVo.setFridgecode(userVo.getUser().getFridgecode());
		System.out.println("=============== 기존 재료 코드 ===============" + originalfridgeBoxVo.getIngredientcode());
		System.out.println("=============== 기존 생성 시간 ===============" + originalfridgeBoxVo.getIngredientcreateddate());
		System.out.println("=============== 기존 냉장 코드 ===============" + userVo.getUser().getFridgecode());

		// 바꾸고싶은 내용
		fridgeBoxVo.setStoragecode(storagecode);
		fridgeBoxVo.setExpiredate(expiredate);
		fridgeBoxVo.setIngredientquantityinfridgebox(ingredientquantityinfridgebox);
		System.out.println("=============== 설정된 저장 위치 ===============" + fridgeBoxVo.getStoragecode());
		System.out.println("=============== 설정된 유통 기한 ===============" + fridgeBoxVo.getExpiredate());
		System.out
				.println("=============== 설정된 저장 수량 ===============" + fridgeBoxVo.getIngredientquantityinfridgebox());

		fridgeService.changeFridgeBox(originalfridgeBoxVo, fridgeBoxVo);
		return "redirect:/app/testfridge";
	}

	// AppFridgeBox 삭제
	@DeleteMapping("/deletefridgebox")
	public String createFridgeBox3(FridgeBoxVo fridgeBoxVo, String ingredientcreateddate, Integer ingredientcode,
			@AuthenticationPrincipal PrincipalDetails userVo) {
		fridgeBoxVo.setIngredientcode(ingredientcode);
		fridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
		fridgeBoxVo.setFridgecode(userVo.getUser().getFridgecode());
		System.out.println("=============== 설정된 등록 시간 ===============" + fridgeBoxVo.getIngredientcreateddate());
		System.out.println("=============== 설정된 재료 코드 ===============" + fridgeBoxVo.getIngredientcode());
		System.out.println("=============== 설정된 냉장 코드 ===============" + userVo.getUser().getFridgecode());
		fridgeService.deleteFridgeBox(fridgeBoxVo);
		return "redirect:/app/testfridge";
	}

	// =============
	@PostMapping("/updatecookbasket")
	@ResponseBody
	public void cookbasketVo2(Integer cookcode, Integer cookquantityinbasket,
			@AuthenticationPrincipal PrincipalDetails user) {
		System.out.println("=============== 장바구니 요리 수량 변경 ===============");
		System.out.println(cookcode + " " + cookquantityinbasket + " " + user.getUser().getUsercode());
		ingredientService.updatecookbasket(cookquantityinbasket, user.getUser().getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
	}

	@PostMapping("/deletecookbasket")
	public String cookbasketVo3(Integer cookcode, @AuthenticationPrincipal PrincipalDetails user) {
		System.out.println("=============== 장바구니 요리 삭제 ===============");
		System.out.println(cookcode + " " + user.getUser().getUsercode());
		ingredientService.deletecookbasket(user.getUser().getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
		return "redirect:/basket";
	}

	@GetMapping("/checklist")
	public String checklist(Model model, @AuthenticationPrincipal PrincipalDetails user) {

		Integer usercode = user.getUser().getUsercode();
		String fridgecode = user.getUser().getFridgecode();
		List<IngredientVo> ingredientchecklist = ingredientService.ingredientchecklist(usercode, fridgecode);
		model.addAttribute("ingredientlist", ingredientchecklist);

		return "checklist";
	}

	@GetMapping("/mypage")
	public String mypage(@AuthenticationPrincipal PrincipalDetails details, Model model) throws Exception {
		UserVo userlist = userService.findById(details.getUsername());
		model.addAttribute("account", userlist);
		return "app-mypage";
	}

	// mypage 수정요청시 업데이트 user
	@PostMapping("/mypage")
	public String updateUser(UserVo userVo, @AuthenticationPrincipal PrincipalDetails user, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

//		HttpSession session = request.getSession(false);
//		username = request.getUserPrincipal().getName();// username
		// 여기가 진짜 name
//		System.out.println("usercode: " + username); // user9797 Id username
//		System.out.println("받아온 이름 : " + name); // 주희
		userVo.setUsercode(user.getUser().getUsercode());
		System.out.println("update service 탄 전");
		userService.updateUser(userVo);
		System.out.println("update service 탄 후");

//		User persistence = (User) userService.findById(username);
//		name = userVo.getName();
//		persistence.setUsername(name);
		// 세션 등록
//		Authentication authentication = multipleLoginSecurityConfig.authenticationManagerBean().authenticate(SecurityContextHolder.getContext().getAuthentication());
//		System.out.println(authentication);
//		session.setAttribute(name, "name");
		return "redirect:/app";
	}

//	public String login_Process(HttpServletRequest req, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//	
//	//null check 필수
//	HttpSession session = req.getSession(false);
//	
//	//방법 1
//	System.out.println(customUserDetails);
//	
//	//방법2
//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	CustomUserDetails customUserDetailss = (CustomUserDetails) authentication.getPrincipal();
//	System.out.println(customUserDetailss.getId() + customUserDetailss.getRealname() + customUserDetailss.getAuthorities());
//	
//	//방법3
//	User tmp_user = (User)authentication.getPrincipal();
//	System.out.println(tmp_user);
//
//
//
//}

}
