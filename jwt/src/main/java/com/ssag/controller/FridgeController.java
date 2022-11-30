package com.ssag.controller;

import java.time.LocalTime;
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
import org.springframework.web.servlet.ModelAndView;

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
//	String[] procedureList
	Map<Integer, String> result = new HashMap<>();

	// 냉장고에 재료수기 등록하는 페이지
	@PostMapping("/fridgebox")
	public String createFridgeBox(FridgeBoxVo fridgeBoxVo, IngredientVo ingredientVo,
			@AuthenticationPrincipal UserVo userVo) {
		fridgeBoxVo.setIngredientcode(ingredientVo.getIngredientcode());
		fridgeBoxVo.setStoragecode(fridgeBoxVo.getStoragecode());
		fridgeBoxVo.setFridgecode(userVo.getFridgecode());
		System.out.println("FridgeCode : " + userVo.getFridgecode());

		System.out
				.println("=====================================setIngredientcode : " + fridgeBoxVo.getIngredientcode());
//		fridgeBoxVo.setIngredientcreateddate(LocalDate.now());
		fridgeService.createFridgeBox(fridgeBoxVo);
		return "redirect:/myFridge";
	}

	// 그 재료선택 창
//	@GetMapping("/fridgebox")
//	public String fridgeBox(Model model) {
//		System.out.println("여기는 controller : fridgeBox ");
//
////		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
////		model.addAttribute("ingredientList3", ingredientList3);
//		// userService.addUser(userVo);
//
//		return "myFridgeBox";
//	}

	LocalTime now = LocalTime.now();

//	String formatedNow = now.format(formatter);

	List<FridgeBoardVo> memoList = new ArrayList<FridgeBoardVo>();

	// 메인 냉장고 페이지!!
	@GetMapping("/myFridge")
	public String addFridge(Authentication authentication, @AuthenticationPrincipal UserVo userVo, Model model,
			FridgeBoardVo fridgeBoardVo) throws Exception {
		System.out.println("Fridge Contrller MyFridgeBox 진입!!!");
		FridgeVo fridgeVo = fridgeService.addFridge(userVo); // service에서 만든 값
		System.out.println("controller FridgeVo : " + fridgeVo);
		FridgeVo fridgeList = fridgeService.userfridge(fridgeVo.getFridgecode());
		System.out.println("FridgeVO.getCode11 : " + fridgeList.getFridgecode());
		// *********************************************** 게시판

		String fridgecode = userVo.getFridgecode();
		memoList = userService.memoList(fridgecode);

		System.out.println("MemoList!!! : " + memoList);
		model.addAttribute("memoList", memoList);

		// *********************************직접입력 재료 쏴줌
		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);
		
		 // ================================================== 냉장고 재료보여줌
	    String userFridgeCode = userVo.getFridgecode();
	    System.out.println("principal FridgeCode : " + userFridgeCode);
	    fridgeService.selectMyFridge(userFridgeCode);
	    fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
	    System.out.println(fridgeBoxList.get(0));
	    model.addAttribute("fridgeBoxList", fridgeBoxList);
		return "myfridge";
	}

	@PostMapping("/updateFridge")
	public String updateFridgeBox(Authentication authentication, Model model, FridgeBoxVo fridgeBoxVo) {
		fridgeBoxVo.setIngredientcode(261);
		fridgeBoxVo.setFridgecode("5");
//		fridgeBoxVo.setIngredientcreateddate(LocalDate.now());
		fridgeService.createFridgeBox(fridgeBoxVo);

		// PrincipalDetails principal = (PrincipalDetails)
		// authentication.getPrincipal();
		// String username = principal.getUserVo().getUsername();
		// //세션 객체 안에 있는 ID정보 저장

		System.out.println("C: 회원정보수정 GET의 getQuantity " + fridgeBoxVo.getIngredientquantityinfridgebox());
		return "fridgeBox2";
	}

	// @GetMapping("/productkeyword")
	// public String searchIngrdient2() {
	//
	// return "index";
	// }
	// index 감자 검색
	@PostMapping("/searchresult")
	public String searchIngrdient(SimilarnameVo similarnameVo, Model model, String similar) {
		System.out.println(similarnameVo.getSimilar());
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		System.out.println("여기 밑에 similarnameList==========================================================d");
//		System.out.println(similarnameList);
		System.out.println(similarnameList.size());
		// System.out.println(similarnameList.get(0).getIngredientVo());
		// System.out.println(similarnameList.get(0).getIngredientVo().getName());
		// ModelAndView mv = new ModelAndView();
		// mv.addObject("list", list);
		// mv.addObject("list", fridgeService.getKeyword(similar));
		model.addAttribute("cardlist", similarnameList);
		System.out.println("***********************************************"
				+ similarnameList.get(0).getMerchandiseVo22().getItemname());

		System.out.println("띠용오ㅓ오옹크기느은");
		// System.out.println("띠용오ㅓ오옹"+list.get(0).getIngredientVo().getName());
		return "search-result";
	}

	@GetMapping("/searchresult")
	public ModelAndView searchresult(@RequestParam(value = "similar") String similar) {
		List<SimilarnameVo> similarnameVo = fridgeService.getKeyword(similar);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("cardlist");
		mv.addObject(similarnameVo);
		System.out.println(similarnameList.get(0).getIngredientVo22());
		System.out.println(similarnameList.get(0).getIngredientVo22().getIngredientname());
		System.out.println("***********************************************"
				+ similarnameList.get(0).getMerchandiseVo22().getItemname());
		return mv;
	}

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
//		HashMap<String, ArrayList<SimilarnameVo>> keyIdx = ingredientService.joinDic(similar);

		model.addAttribute("procedureList", procedureList);
		System.out.println("***********************************************"
				+ procedureList.get(0).getIngredientVo22().getIngredientname());
//		ArrayList<SimilarnameVo> list = keyIdx.get(procedureList.get(0).getCookVo22().getCookname());
//		System.out.println("________________________________-" + list);
//		System.out.println("________________________________-" + list.size());
//		System.out.println("________________________________-" + list.get(0).getIngredientVo22().getIngredientname());
//		System.out.println("________________________________-" + list.get(1).getIngredientVo22().getIngredientname());

		// ----------------------------------------------------------------------------
		return procedureList;
	}

	// 튀김 검색하면 튀김레시피 나옴
	@GetMapping("/recipeList")
	public String recipeList() {
		return "recipeList";
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

	@GetMapping("/")
	public String home() {
		return "index";
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

//		
		return list;
	}

	// 임시 주석
	// 내 냉장고에 있는 물건들 리스트 보기
	List<FridgeBoxVo> fridgeBoxList = new ArrayList<FridgeBoxVo>();

	@GetMapping("/myFridgeBoxList")
	@ResponseBody
	public List<FridgeBoxVo> myfridgeBoxList(@AuthenticationPrincipal UserVo userVo, Authentication authentication) {
//		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

		String userFridgeCode = userVo.getFridgecode();
		System.out.println("principal FridgeCode : " + userFridgeCode);
		fridgeService.selectMyFridge(userFridgeCode);
		fridgeBoxList = fridgeService.selectMyFridge(userFridgeCode);
		return fridgeBoxList;
	}

}