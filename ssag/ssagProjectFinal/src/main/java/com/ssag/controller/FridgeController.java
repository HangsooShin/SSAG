package com.ssag.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.model.CompanyVo;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.UserVo;
import com.ssag.service.FridgeService;
import com.ssag.service.IngredientService;
import com.ssag.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
// @RequestMapping("fridge")
public class FridgeController {

	@Autowired
	private FridgeService fridgeService;

	@Autowired
	private UserService userService;

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private CookIngredientListVo cookIngredientListVo;

	@Autowired
	private FridgeBoxVo fridgeBoxVo;

	@Autowired
	private CookVo cookVo;

//	private final PasswordEncoder passwordEncoder;
	
	Logger logger = LoggerFactory.getLogger("com.ssag.controller.FridgeController");

	List<SimilarnameVo> similarnameList = new ArrayList<SimilarnameVo>();
	List<SimilarnameVo> procedureList = new ArrayList<SimilarnameVo>();
	List<FridgeBoardVo> memoList = new ArrayList<FridgeBoardVo>();
	List<CookVo> recipeList = new ArrayList<>();
	List<FridgeBoxVo> fridgeBoxList = new ArrayList<FridgeBoxVo>();

	// 냉장고에 재료수기 등록 모달창
	@PostMapping("/fridgebox")
	public String createFridgeBox(FridgeBoxVo fridgeBoxVo, IngredientVo ingredientVo,
			@AuthenticationPrincipal PrincipalDetails details) {
		fridgeBoxVo.setIngredientcode(ingredientVo.getIngredientcode());
		fridgeBoxVo.setStoragecode(fridgeBoxVo.getStoragecode());

		fridgeBoxVo.setFridgecode(details.getUser().getFridgecode());
		log.info("등록자 냉장고 코드 : " + details.getUser().getFridgecode());

		fridgeService.createFridgeBox(fridgeBoxVo);
		return "redirect:/myFridge";
	}

	

	// 메인 냉장고 페이지
	@GetMapping("/myFridge")
	public String addFridge(@AuthenticationPrincipal PrincipalDetails principal, Model model, UserVo user,
			FridgeBoardVo fridgeBoardVo) throws Exception {
		String userFridgeCode = principal.getUser().getFridgecode();
		logger.info("Fridge Contrller MyFridgeBox 진입!!!");

		// *********************************************** 게시판
		
		memoList = userService.memoList(userFridgeCode);
		model.addAttribute("memoList", memoList);

		// *********************************************** 재료 리스트 보여줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);

		// *********************************************** 냉장고 재료보여줌
		
		fridgeService.selectMyFridge(userFridgeCode);
		fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
		model.addAttribute("fridgeBoxList", fridgeBoxList);
		return "myfridge";
	}

//	@PostMapping("/updateFridge")
//	public String updateFridgeBox(Authentication authentication, Model model, FridgeBoxVo fridgeBoxVo) {
//		fridgeBoxVo.setIngredientcode(261);
//		fridgeService.createFridgeBox(fridgeBoxVo);
//		return "fridgeBox2";
//	}

	// main 감자 검색하면 감자 상품정보 나옴
	@GetMapping("/searchresult")
	public String searchresultGet(SimilarnameVo similarnameVo, Model model, @RequestParam("similar") String similar) {

		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		model.addAttribute("cardlist", similarnameList);

		return "search-result";
	}

	// 감자 양파 검색했을 때 짜장밥 나오는 프로시저
	@PostMapping("/procedureList")
	@ResponseBody
	public List<SimilarnameVo> procedure2(SimilarnameVo similarnameVo, String similar, Model model) {
		fridgeService.procedure2(similarnameVo.getSimilar());
		procedureList = fridgeService.procedure2(similar);
		model.addAttribute("procedureList", procedureList);
		return procedureList;
	}

	// 엡 - 감자 양파 >> 짜장밥 검색데이터페이지
	@PostMapping("/apprecipetypeingredient")
	@ResponseBody
	public List<CookIngredientListVo> procedure3(SimilarnameVo similarnameVo, String similar, Model model) {
		System.out.println("=============== 검색어로 입력된 데이터 ================" + similarnameVo.getSimilar());
		List<CookIngredientListVo> list = ingredientService.joinDic(similarnameVo.getSimilar());
//		System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
		return list;
	}

	// 튀김 검색하면 튀김레시피 나옴
	@GetMapping("/recipeList")
	public String recipeList() {
		return "recipeList";
	}

	// 레시피 리스트 검색
	@PostMapping("/recipeList")
	@ResponseBody
	public List<CookVo> recipeList(String name, CookVo cookVo, Model model) {
		fridgeService.selectRecipe(cookVo.getCookname());
		recipeList = fridgeService.selectRecipe(name);
		model.addAttribute("recipeList", recipeList);
		return recipeList;
	}

	
	
}