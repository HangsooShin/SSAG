package com.ssag.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.model.CookIngredientListVo;
import com.ssag.model.CookVo;
import com.ssag.model.CookbasketListVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.UserVo;
import com.ssag.service.FridgeService;
import com.ssag.service.IngredientService;
import com.ssag.service.UserService;

@Controller
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

	List<IngredientVo> ingredientList = new ArrayList<IngredientVo>();
	List<SimilarnameVo> similarnameList = new ArrayList<SimilarnameVo>();
	List<SimilarnameVo> procedureList = new ArrayList<SimilarnameVo>();
	Map<Integer, String> result = new HashMap<>();

	
	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	// 냉장고에 재료수기 등록하는 페이지
	@PostMapping("/fridgebox")
	public String createFridgeBox(FridgeBoxVo fridgeBoxVo, IngredientVo ingredientVo,
			@AuthenticationPrincipal UserVo userVo) {
		fridgeBoxVo.setIngredientcode(ingredientVo.getIngredientcode());
		fridgeBoxVo.setStoragecode(fridgeBoxVo.getStoragecode());
		fridgeBoxVo.setFridgecode(userVo.getFridgecode());
		System.out.println("=============== 설정된 FridgeCode ==================="+userVo.getFridgecode());
		System.out.println("=============== 설정된 Ingredientcode ==============="+fridgeBoxVo.getIngredientcode());
		fridgeService.createFridgeBox(fridgeBoxVo);
		return "redirect:/myFridge";
	}
	
	@PostMapping("/appfridgebox")
	public String createFridgeBox2(String ingredientcode, String ingredientquantityinfridgebox, String storagecode, String expiredate,
			FridgeBoxVo fridgeBoxVo, @AuthenticationPrincipal UserVo userVo) {
		
//		System.out.println(ingredientcode);
//		System.out.println(ingredientquantityinfridgebox);
//		System.out.println(storagecode);
//		System.out.println(expiredate);
		
		String[] storagecodelist = storagecode.split(",");
		String[] ingredientcodelist = ingredientcode.split(",");
		String[] ingredientquantityinfridgeboxlist = ingredientquantityinfridgebox.split(",");
		String[] expiredatelist = expiredate.split(",");
		
		fridgeBoxVo = new FridgeBoxVo();
		
		for (int i=0; i < storagecodelist.length; i++) {
			fridgeBoxVo.setStoragecode(Integer.parseInt(storagecodelist[i]));
			fridgeBoxVo.setIngredientcode(Integer.parseInt(ingredientcodelist[i]));
			fridgeBoxVo.setIngredientquantityinfridgebox(Integer.parseInt(ingredientquantityinfridgeboxlist[i]));
			fridgeBoxVo.setExpiredate(expiredatelist[i]);
			fridgeBoxVo.setFridgecode(userVo.getFridgecode());
			
			System.out.println("=============== 설정된 FridgeCode ==================="+userVo.getFridgecode());
			System.out.println("=============== 설정된 Ingredientcode ===============" + fridgeBoxVo.getIngredientcode());
			
			fridgeService.createFridgeBox(fridgeBoxVo);
		}
		return "redirect:/testfridge";
	}
	
	@PostMapping("/appchangefridgebox")
	public String changeFridgeBox3(FridgeBoxVo originalfridgeBoxVo, FridgeBoxVo fridgeBoxVo, String ingredientcreateddate, String expiredate,
			Integer ingredientcode, Integer storagecode, Integer ingredientquantityinfridgebox, @AuthenticationPrincipal UserVo userVo) {
		// DB에서 선택할 PK 기준
		originalfridgeBoxVo.setIngredientcode(ingredientcode);
		originalfridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
		originalfridgeBoxVo.setFridgecode(userVo.getFridgecode());
		System.out.println("=============== 기존 재료 코드 ==============="+originalfridgeBoxVo.getIngredientcode());
		System.out.println("=============== 기존 생성 시간 ==============="+originalfridgeBoxVo.getIngredientcreateddate());
		System.out.println("=============== 기존 냉장 코드 ==============="+userVo.getFridgecode());
		
		//바꾸고싶은 내용
		fridgeBoxVo.setStoragecode(storagecode);
		fridgeBoxVo.setExpiredate(expiredate);
		fridgeBoxVo.setIngredientquantityinfridgebox(ingredientquantityinfridgebox);
		System.out.println("=============== 설정된 저장 위치 ==============="+fridgeBoxVo.getStoragecode());
		System.out.println("=============== 설정된 유통 기한 ==============="+fridgeBoxVo.getExpiredate());
		System.out.println("=============== 설정된 저장 수량 ==============="+fridgeBoxVo.getIngredientquantityinfridgebox());
		
		fridgeService.changeFridgeBox(originalfridgeBoxVo, fridgeBoxVo);
		return "redirect:/testfridge";
	}

	@PostMapping("/appdeletefridgebox")
	public String createFridgeBox3(FridgeBoxVo fridgeBoxVo, String ingredientcreateddate, Integer ingredientcode,
			@AuthenticationPrincipal UserVo userVo) {
		fridgeBoxVo.setIngredientcode(ingredientcode);
		fridgeBoxVo.setIngredientcreateddate(ingredientcreateddate);
		fridgeBoxVo.setFridgecode(userVo.getFridgecode());
		System.out.println("=============== 설정된 등록 시간 ==============="+fridgeBoxVo.getIngredientcreateddate());
		System.out.println("=============== 설정된 재료 코드 ==============="+fridgeBoxVo.getIngredientcode());
		System.out.println("=============== 설정된 냉장 코드 ==============="+userVo.getFridgecode());
		fridgeService.deleteFridgeBox(fridgeBoxVo);
		return "redirect:/testfridge";
	}
	
	List<FridgeBoardVo> memoList = new ArrayList<FridgeBoardVo>();

	// 앱 냉장고 페이지!!
	@GetMapping("/testfridge")
	public String addappFridge(Authentication authentication, @AuthenticationPrincipal UserVo user, Model model,
			FridgeBoardVo fridgeBoardVo) throws Exception {
		System.out.println("=============== FridgeContrller MyFridgeBox 진입 ===============");
		FridgeVo fridgeVo = fridgeService.addFridge(user);
		FridgeVo fridgeList = fridgeService.userfridge(fridgeVo.getFridgecode());
		System.out.println("=============== Controller FridgeVo ===============" + fridgeVo);
		System.out.println("=============== FridgeVO.getCode11 ================" + fridgeList.getFridgecode());

		// *********************************************** 게시판
		String fridgecode =user.getFridgecode();

		// *********************************직접입력 재료 쏴줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);
		
		// ================================================== 냉장고 재료보여줌
		String userFridgeCode = user.getFridgecode();
	    fridgeService.selectMyFridge(userFridgeCode);
	    fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
	    model.addAttribute("fridgeBoxList", fridgeBoxList);
	    return "testfridge";
	}
	
	// 메인 냉장고 페이지!!
	@GetMapping("/myFridge")
	public String addFridge(Authentication authentication, @AuthenticationPrincipal UserVo user, Model model,
			FridgeBoardVo fridgeBoardVo) throws Exception {
		System.out.println("=============== FridgeContrller MyFridgeBox 진입 ===============");
//		FridgeVo fridgeVo = fridgeService.addFridge(userVo); // service에서 만든 값
		FridgeVo fridgeVo = fridgeService.addFridge(user);
		FridgeVo fridgeList = fridgeService.userfridge(fridgeVo.getFridgecode());
		System.out.println("=============== Controller FridgeVo ===============" + fridgeVo);
		System.out.println("=============== FridgeVO.getCode11 ================" + fridgeList.getFridgecode());
		
		// *********************************************** 게시판
		String fridgecode =user.getFridgecode();
		memoList = userService.memoList(fridgecode,user.getUsercode());
		System.out.println("MemoList!!! : " + memoList);
		model.addAttribute("memoList", memoList);

		// *********************************직접입력 재료 쏴줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);
		
		// ================================================== 냉장고 재료보여줌
		String userFridgeCode =user.getFridgecode();
	    fridgeService.selectMyFridge(userFridgeCode);
	    fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
	    model.addAttribute("fridgeBoxList", fridgeBoxList);
	    return "myfridge";
		
	}
	
	@PostMapping("/updateFridge")
	public String updateFridgeBox(Authentication authentication, Model model, FridgeBoxVo fridgeBoxVo) {
		fridgeBoxVo.setIngredientcode(261);
		fridgeBoxVo.setFridgecode("5");
		fridgeService.createFridgeBox(fridgeBoxVo);

		// PrincipalDetails principal = (PrincipalDetails)
		// authentication.getPrincipal();
		// String username = principal.getUserVo().getUsername();
		// //세션 객체 안에 있는 ID정보 저장

		System.out.println("C: 회원정보수정 GET의 getQuantity " + fridgeBoxVo.getIngredientquantityinfridgebox());
		return "fridgeBox2";
	}

	// app index 감자 검색 데이터 페이지
	@PostMapping(value="/testmerch")
	@ResponseBody
	public List<SimilarnameVo> searchIngrdient(SimilarnameVo similarnameVo, Model model, String similar) {
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		return similarnameList;
	}
	
//	@GetMapping("/testmerchandiseresult")
//	@ResponseBody
//	public List<SimilarnameVo> searchIngrdient(SimilarnameVo similarnameVo, Model model, String similar) {
//		System.out.println(similar);
//		fridgeService.getKeyword(similarnameVo.getSimilar());
//		similarnameList = fridgeService.getKeyword(similar);
//		System.out.println("여기 밑에 similarnameList==========================================================d");
//		System.out.println(similarnameList);
//		System.out.println(similarnameList.size());
//		model.addAttribute("cardlist", similarnameList);
//		System.out.println("***********************************************" + similarnameList.get(0).getMerchandiseVo22().getItemname());
//		System.out.println("띠용오ㅓ오옹크기느은");
//		return similarnameList;
//
//	}
	

	@GetMapping("/procedureList")
	public String procedureList() {
		return "procedureTest";
	}

	// 감자 양파 검색했을 때 짜장밥 나오는 프로시저
	@PostMapping("/procedureList")
	@ResponseBody
	public List<SimilarnameVo> procedure2(SimilarnameVo similarnameVo, String similar, Model model) {
		System.out.println(similarnameVo.getSimilar());
		fridgeService.procedure2(similarnameVo.getSimilar());
		procedureList = fridgeService.procedure2(similar);
		model.addAttribute("procedureList", procedureList);
		System.out.println("***********************************************"
				+ procedureList.get(0).getIngredientVo22().getIngredientname());
		return procedureList;
	}
	
	
	// 엡 - 감자 양파 >> 짜장밥 검색데이터페이지
	@PostMapping("/apprecipetypeingredient")
	@ResponseBody
	public List<CookIngredientListVo> procedure3(SimilarnameVo similarnameVo, String similar, Model model) {
		System.out.println("=============== 검색어로 입력된 데이터 ================"+similarnameVo.getSimilar());
		List<CookIngredientListVo> list = ingredientService.joinDic(similarnameVo.getSimilar());
//		System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
		return list;
	}
	
	// 앱 - 튀김 >> 튀김레시피 검색데이터페이지
	@PostMapping("/apprecipetypename")
	@ResponseBody
	public List<CookIngredientListVo> procedure4(SimilarnameVo similarnameVo, String similar, Model model) {
		System.out.println("=============== 검색어로 입력된 데이터 ================"+similarnameVo.getSimilar());
		List<CookIngredientListVo> list = ingredientService.joinDic2(similarnameVo.getSimilar());
		System.out.println("=============== 데이터가 나오는지 확인 ================"+ list);
		return list;
	}

	List<CookVo> recipeList = new ArrayList<>();

	@PostMapping("/recipeList")
	@ResponseBody
	public List<CookVo> recipeList(String name, CookVo cookVo, Model model) {
		System.out.println("controller name 인데 이게 왜 안들어오지 : " + cookVo.getCookname());
		fridgeService.selectRecipe(cookVo.getCookname());
		recipeList = fridgeService.selectRecipe(name);
		System.out.println(recipeList);
		System.out.println(recipeList.size());
		model.addAttribute("recipeList", recipeList);
		return recipeList;
	}
	

	@GetMapping("/ingredientSearch")
	public String searchresult() {

		return "ingredientSearch";
	}

	@PostMapping("/ingredientSearch")
//	@ResponseBody
	public List<CookIngredientListVo> searchresult1(String similar, Model model, SimilarnameVo similarnameVo) {

		System.out.println("similar name : " + similar);
		fridgeService.procedure2(similarnameVo.getSimilar());
		procedureList = fridgeService.procedure2(similar);
//		HashMap<String, ArrayList<IngredientVo>> keyIdx = ingredientService.joinDic(similar);
		List<CookIngredientListVo> list = ingredientService.joinDic(similar);

		model.addAttribute("list", list);
		System.out.println(
				"***********************************************" + procedureList.get(0).getIngredientVo22().getIngredientname());
		return list;
	}

	// 내 냉장고에 있는 물건들 리스트 보기
	List<FridgeBoxVo> fridgeBoxList = new ArrayList<FridgeBoxVo>();

	@GetMapping("/myFridgeBoxList")
	@ResponseBody
	public List<FridgeBoxVo> myfridgeBoxList(@AuthenticationPrincipal UserVo userVo, Authentication authentication) {

		String userFridgeCode = userVo.getFridgecode();
		System.out.println("principal FridgeCode : " + userFridgeCode);
		fridgeService.selectMyFridge(userFridgeCode);
		fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
		return fridgeBoxList;
	}
	
	
	@GetMapping("/searchresult")
	public String searchresultGet(SimilarnameVo similarnameVo, Model model,@RequestParam("similar")String similar) {
		System.out.println(similar);
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		System.out.println("===============similarnameList===============" + similarnameList);
		model.addAttribute("cardlist", similarnameList);
		System.out.println("***********************************************" + similarnameList.get(0).getMerchandiseVo22().getItemname());
		System.out.println("띠용오ㅓ오옹크기느은");
		return "search-result";
	}
	
	@PostMapping("/testmerchandiseresult")
	public String searchresulttest(String similar,Model model) {
		System.out.println("testmerchandiseresult 진입");
		model.addAttribute("similar",similar);
		return "testmerchandiseresult";
	}
	
	@PostMapping("/getcookbasket")
	@ResponseBody
	public void cookbasketVo (Integer cookcode, Integer cookquantityinbasket, @AuthenticationPrincipal UserVo user) {
		System.out.println("=============== 장바구니 추가 ===============");
		System.out.println(cookcode +" "+ cookquantityinbasket +" "+ user.getUsercode());
		ingredientService.updatecookcode(cookquantityinbasket, user.getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
	}
	
	@GetMapping("/basket")
	public String cookbasketpage (@AuthenticationPrincipal UserVo user, Model model) {
		System.out.println("=============== 장바구니 확인 =============== + usercode : " + user.getUsercode());
		
		try {
			List<CookbasketListVo> basketlist = ingredientService.cookbasket(user.getUsercode(), user.getFridgecode());
			System.out.println("데이터 받아옴");
			model.addAttribute("basketlist", basketlist);
			System.out.println("애드어트리뷰트까지 다함"+basketlist.get(0).getIngredientVoList());
			
		} catch (Exception e) {
			System.out.println("=============== 에러발생 ===============");
		}

		return "basket";
	}
	
	@PostMapping("/updatecookbasket")
	@ResponseBody
	public void cookbasketVo2 (Integer cookcode, Integer cookquantityinbasket, @AuthenticationPrincipal UserVo user) {
		System.out.println("=============== 장바구니 요리 수량 변경 ===============");
		System.out.println(cookcode +" "+ cookquantityinbasket +" "+ user.getUsercode());
		ingredientService.updatecookbasket(cookquantityinbasket, user.getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
	}
	
	@PostMapping("/deletecookbasket")
	public String cookbasketVo3 (Integer cookcode, @AuthenticationPrincipal UserVo user) {
		System.out.println("=============== 장바구니 요리 삭제 ===============");
		System.out.println(cookcode +" "+ user.getUsercode());
		ingredientService.deletecookbasket(user.getUsercode(), cookcode);
		System.out.println("끝났는지 확인");
		return "redirect:/basket";
	}
	
	@GetMapping("/checklist")
	public String checklist (Model model, @AuthenticationPrincipal UserVo user) {
		Integer usercode = user.getUsercode();
		String fridgecode = user.getFridgecode();
		List<IngredientVo> ingredientchecklist = ingredientService.ingredientchecklist(usercode, fridgecode);
		model.addAttribute("ingredientlist",ingredientchecklist);
		return "/checklist";
	}
	
//	@PostMapping("/companyrecipt")
//	@ResponseBody
//	public List<ShoppingcartVo> shoppingcart (@AuthenticationPrincipal UserVo user) {
//		
//		shoppingcart = ingredientService.getrecipt(user.getUsercode());
//		
//		return shoppingcart;
//	} 
//	
	
	
	
	
}