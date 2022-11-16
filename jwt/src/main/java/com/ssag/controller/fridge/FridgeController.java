package com.ssag.controller.fridge;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.model.CookVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.StringSplitVo;
import com.ssag.service.FridgeService;
import com.ssag.service.UserService;

@Controller
//@RequestMapping("fridge")
public class FridgeController {

	@Autowired
	private FridgeService fridgeService;

	@Autowired
	private UserService userService;

	@Autowired
	private FridgeBoxVo fridgeBoxVo;

	@Autowired
	private IngredientVo ingredientVo;
	
	@Autowired
	private CookVo cookVo;

	ModelAndView mv;

	List<IngredientVo> ingredientList = new ArrayList<IngredientVo>();
	List<SimilarnameVo> similarnameList = new ArrayList<SimilarnameVo>();
	List<SimilarnameVo> procedureList = new ArrayList<SimilarnameVo>();
	
	
	Map<Integer, String> result = new HashMap<>();

	@PostMapping("/fridgebox")
	public String createFridgeBox(FridgeBoxVo fridgeBoxVo, IngredientVo ingredientVo) {
		fridgeBoxVo.setIngredientcode(ingredientVo.getCode());
		fridgeBoxVo.setStoragecode(1);
		
		System.out
				.println("=====================================setIngredientcode : " + fridgeBoxVo.getIngredientcode());
		fridgeBoxVo.setCreateddate(LocalDate.now());
		fridgeService.createFridgeBox(fridgeBoxVo);
		return "redirect:/";
	}
	//그 재료선택 창 
	@GetMapping("/fridgebox")
	public String fridgeBox(Model model) {
		System.out.println("여기는 controller : fridgeBox ");

		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);
//		userService.addUser(userVo);

		return "fridgebox2";
	}

	List<FridgeVo> fridgeVo2 = new ArrayList<FridgeVo>();
	
	@GetMapping("/myFridgeBox")
	public String addFridge(Authentication authentication) {
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		System.out.println("Fridge Contrller MyFridgeBox 진입!!!");
		FridgeVo fridgeVo = fridgeService.addFridge(authentication); //service에서 만든 값
		
		fridgeVo2 = fridgeService.userfridge(fridgeVo.getOwner()); //insert해서 가져오려고 함
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		System.out.println("FridgeVO.getCode22 : " + fridgeVo);
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		
		String fridgeCode = fridgeVo2.get(0).getCode();
		Integer code = fridgeVo2.get(0).getOwner(); 
		userService.addUserFridgeCode(fridgeCode, code);
		return "fridgeBox2";
	}
	
	@GetMapping("/test")
	public String test() {

		return "test";
	}
	
	@PostMapping("/test")
	public String test(StringSplitVo stringSplitVo) {
		
		System.out.println("여기 string SplitVo Name 들어간다아아아아아 : "+stringSplitVo.getIngredientNameList());
		
		return "test";
	}
	
	@PostMapping("/updateFridge")
	public String updateFridgeBox(Authentication authentication, Model model,FridgeBoxVo fridgeBoxVo) {
		fridgeBoxVo.setIngredientcode(261);
		fridgeBoxVo.setFridgecode("5");
		fridgeBoxVo.setCreateddate(LocalDate.now());
		fridgeService.createFridgeBox(fridgeBoxVo);
		
//		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//		String username = principal.getUserVo().getUsername();
//		//세션 객체 안에 있는 ID정보 저장
		
		System.out.println("C: 회원정보수정 GET의 getQuantity "+fridgeBoxVo.getQuantity());		
		return "fridgeBox2";
	}
	
//	@GetMapping("/productkeyword")
//	public String searchIngrdient2() {
//
//		return "index";
//	}
	// index 감자 검색
	@PostMapping("/productkeyword")
	public String searchIngrdient(SimilarnameVo similarnameVo, Model model,String similar) {
		System.out.println(similarnameVo.getSimilar());
		fridgeService.getKeyword(similarnameVo.getSimilar());
		similarnameList = fridgeService.getKeyword(similar);
		System.out.println(similarnameList);
		System.out.println(similarnameList.size());
//		System.out.println(similarnameList.get(0).getIngredientVo());
//		System.out.println(similarnameList.get(0).getIngredientVo().getName());
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("list", list);
//		mv.addObject("list", fridgeService.getKeyword(similar));
		model.addAttribute("card-list", similarnameList);
		System.out.println("***********************************************"+similarnameList.get(0).getMerchandiseVo22().getItemname());
		
		System.out.println("띠용오ㅓ오옹크기느은");
//		System.out.println("띠용오ㅓ오옹"+list.get(0).getIngredientVo().getName());
		return "redirect:/";
	}
	
	
	@GetMapping("/procedureList")
	public String procedureList() {
		return "procedureTest";
	}
	
	//감자 양파 검색했을 때 짜장밥 나오는 프로시저
	@PostMapping("/procedureList")
	@ResponseBody
	public List<SimilarnameVo> procedure2(SimilarnameVo similarnameVo, String similar, Model model) {
		System.out.println(similarnameVo.getSimilar());
		fridgeService.procedure2(similarnameVo.getSimilar());
		procedureList = fridgeService.procedure2(similar);
		
		System.out.println(procedureList);
		System.out.println(procedureList.size());
		model.addAttribute("procedureList", procedureList);
		System.out.println("***********************************************"+procedureList.get(0).getCookVo22().getLink());
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
	public List<CookVo> recipeList(String name,CookVo cookVo, Model model){
		System.out.println("controller name 인데 이게 왜 안들어오지 : "+cookVo.getName());
		fridgeService.selectRecipe(cookVo.getName());
		recipeList =fridgeService.selectRecipe(name);
		System.out.println(recipeList);
		System.out.println(recipeList.size());
		model.addAttribute("recipeList", recipeList);
		return recipeList;
	}
	
	
	
	@GetMapping("/")
	public String home(){
		return "index";
	}
	
}
