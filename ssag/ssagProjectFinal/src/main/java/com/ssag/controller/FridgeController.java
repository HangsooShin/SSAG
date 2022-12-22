package com.ssag.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookVo;
import com.ssag.model.CookbasketListVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.MerchandiseVo;
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
	public String addFridge(@AuthenticationPrincipal PrincipalDetails details, Model model,
			FridgeBoardVo fridgeBoardVo) throws Exception {
		logger.info("Fridge Contrller MyFridgeBox 진입!!!");
		model.addAttribute("userInfo", details.getUser());
		// *********************************************** 게시판

		String fridgecode = details.getUser().getFridgecode();
		memoList = userService.memoList(fridgecode, details.getUser().getUsercode());
		System.out.println("MemoList!!! : " + memoList);
		model.addAttribute("memoList", memoList);

		// *********************************************** 재료 리스트 보여줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);

		// *********************************************** 냉장고 재료보여줌
		String userFridgeCode = userService.findById(details.getUser().getUsername()).getFridgecode();
		System.out.println("과연 변경 후 fridgecode 인가" + userFridgeCode);
		fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
		model.addAttribute("fridgeBoxList", fridgeBoxList);
		return "myfridge";
	}

	// main 감자 검색하면 감자 상품정보 나옴
	@GetMapping("/searchresult")
	public String searchresultGet(SimilarnameVo similarnameVo, Model model, @RequestParam("similar") String similar,@AuthenticationPrincipal PrincipalDetails details) {
		if(details != null) {
			model.addAttribute("userInfo", details.getUser());	
		}
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		model.addAttribute("cardlist", similarnameList);

		return "search-result";
	}


	// 엡 - 감자 양파 >> 짜장밥 검색데이터페이지
	@PostMapping("/recipeList")
	@ResponseBody
	public List<CookIngredientListVo> recipeList(String cookname, Model model) {
		System.out.println("=============== 검색어로 입력된 데이터 ================" + cookname);
		List<CookIngredientListVo> list = ingredientService.joinDic2(cookname);
//		System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
		return list;
	}

	  // 엡 - 감자 양파 >> 짜장밥 검색데이터페이지
	  @PostMapping("/procedureList")
	  @ResponseBody
	  public List<CookIngredientListVo> procedureList(String similar, Model model) {
	    System.out.println("=============== 검색어로 입력된 데이터 ================" + similar);
	    List<CookIngredientListVo> list = ingredientService.joinDic(similar);
	    // System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
	    return list;
	  }


	// 12/21추가

	  //장바구니 추가
	  @PostMapping("/getcookbasket")
	  @ResponseBody
	  public void cookbasketVo(Integer cookcode, Integer cookquantityinbasket, @AuthenticationPrincipal PrincipalDetails details) {
	    System.out.println("=============== 장바구니 추가 ===============");
	    System.out.println(cookcode + " " + cookquantityinbasket + " " + details.getUser().getUsercode());
	    ingredientService.updatecookcode(cookquantityinbasket, details.getUser().getUsercode(), cookcode);
	    System.out.println("끝났는지 확인");
	  }
	  
	  
	// 여기 아래에서 부터는 웹 장바구니
	@GetMapping("/webbasket")
	public String webBasket(Model model, @AuthenticationPrincipal PrincipalDetails details) {
		if(details != null) {
			model.addAttribute("userInfo", details.getUser());	
		}
		List<IngredientVo> ingredientchecklist = ingredientService.ingredientchecklist(details.getUser().getUsercode(),
				details.getUser().getFridgecode());
		List<CookbasketListVo> cookbasket = ingredientService.cookbasket(details.getUser().getUsercode(), details.getUser().getFridgecode());

		System.out.println("ingredientchecklist==" + ingredientchecklist);
		System.out.println("cookbasket==" + cookbasket);

		model.addAttribute("ingredientchecklist", ingredientchecklist);
		model.addAttribute("cookbasket", cookbasket);

		return "webbasket";
	}

	// 여기 아래에서 부터는 price 페이지
	@GetMapping("/price")
	public String ingredientmerchandise(Model model, @AuthenticationPrincipal PrincipalDetails details) {
		if(details != null) {
			model.addAttribute("userInfo", details.getUser());	
		}
		List<MerchandiseVo> ingredientprice = ingredientService.ingredientprice(details.getUser().getUsercode(),
				details.getUser().getFridgecode());
		model.addAttribute("ingredientprice", ingredientprice);
		return "price";
	}

	@PostMapping("/appfridgebox")
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

			System.out
					.println("=============== 설정된 FridgeCode ===================" + details.getUser().getFridgecode());
			System.out.println("=============== 설정된 Ingredientcode ===============" + fridgeBoxVo.getIngredientcode());

			fridgeService.createFridgeBox(fridgeBoxVo);
		}
		return "redirect:/myFridge";
	}
	
	  @PostMapping("/changefridgebox")
	  public String changeFridgeBox4(FridgeBoxVo originalfridgeBoxVo, FridgeBoxVo fridgeBoxVo, String ingredientcreateddate,
	      String expiredate,
	      Integer ingredientcode, Integer storagecode, Integer ingredientquantityinfridgebox,
	      @AuthenticationPrincipal PrincipalDetails details) {
	    // DB에서 선택할 PK 기준
	    originalfridgeBoxVo.setIngredientcode(ingredientcode);
	    originalfridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
	    originalfridgeBoxVo.setFridgecode(details.getUser().getFridgecode());
	    System.out.println("=============== 기존 재료 코드 ===============" + originalfridgeBoxVo.getIngredientcode());
	    System.out.println("=============== 기존 생성 시간 ===============" + originalfridgeBoxVo.getIngredientcreateddate());
	    System.out.println("=============== 기존 냉장 코드 ===============" + details.getUser().getFridgecode());

	    // 바꾸고싶은 내용
	    fridgeBoxVo.setStoragecode(storagecode);
	    fridgeBoxVo.setExpiredate(expiredate);
	    fridgeBoxVo.setIngredientquantityinfridgebox(ingredientquantityinfridgebox);
	    System.out.println("=============== 설정된 저장 위치 ===============" + fridgeBoxVo.getStoragecode());
	    System.out.println("=============== 설정된 유통 기한 ===============" + fridgeBoxVo.getExpiredate());
	    System.out.println("=============== 설정된 저장 수량 ===============" + fridgeBoxVo.getIngredientquantityinfridgebox());

	    fridgeService.changeFridgeBox(originalfridgeBoxVo, fridgeBoxVo);
	    return "redirect:/myFridge";
	  }
	  @PostMapping("/deletefridgebox")
	  public String createFridgeBox4(FridgeBoxVo fridgeBoxVo, String ingredientcreateddate, Integer ingredientcode,
	      @AuthenticationPrincipal PrincipalDetails details) {
	    fridgeBoxVo.setIngredientcode(ingredientcode);
	    fridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
	    fridgeBoxVo.setFridgecode(details.getUser().getFridgecode());
	    System.out.println("=============== 설정된 등록 시간 ===============" + fridgeBoxVo.getIngredientcreateddate());
	    System.out.println("=============== 설정된 재료 코드 ===============" + fridgeBoxVo.getIngredientcode());
	    System.out.println("=============== 설정된 냉장 코드 ===============" + details.getUser().getFridgecode());
	    fridgeService.deleteFridgeBox(fridgeBoxVo);
	    return "redirect:/myFridge";
	  }
	  
	  @GetMapping("/ingredientSearch")
	  public String searchresult() {

	    return "ingredientSearch";
	  }
	  
	  @PostMapping("/deletecookbasket")
	  public String cookbasketVo3(Integer cookcode, @AuthenticationPrincipal PrincipalDetails details) {
	    System.out.println("=============== 장바구니 요리 삭제 ===============");
	    System.out.println(cookcode + " " + details.getUser().getUsercode());
	    ingredientService.deletecookbasket(details.getUser().getUsercode(), cookcode);
	    System.out.println("끝났는지 확인");
	    return "redirect:/basket";
	  }
	  
	  @PostMapping("/updatecookbasket")
	  @ResponseBody
	  public void cookbasketVo2(Integer cookcode, Integer cookquantityinbasket, @AuthenticationPrincipal PrincipalDetails details) {
	    System.out.println("=============== 장바구니 요리 수량 변경 ===============");
	    System.out.println(cookcode + " " + cookquantityinbasket + " " + details.getUser().getUsercode());
	    ingredientService.updatecookbasket(cookquantityinbasket, details.getUser().getUsercode(), cookcode);
	    System.out.println("끝났는지 확인");
	  }


	  @GetMapping("/checklist")
	  public String checklist(Model model, @AuthenticationPrincipal PrincipalDetails details) {
			if(details != null) {
				model.addAttribute("userInfo", details.getUser());	
			}
	    Integer usercode = details.getUser().getUsercode();
	    String fridgecode = details.getUser().getFridgecode();
	    List<IngredientVo> ingredientchecklist = ingredientService.ingredientchecklist(usercode, fridgecode);
	    model.addAttribute("ingredientlist", ingredientchecklist);
	    return "/checklist";
	  }

}
