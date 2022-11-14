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
import org.springframework.web.servlet.ModelAndView;

import com.ssag.config.auth.PrincipalDetails;
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

	ModelAndView mv;

	List<IngredientVo> ingredientList = new ArrayList<IngredientVo>();
	List<SimilarnameVo> similarnameList = new ArrayList<SimilarnameVo>();
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
		Integer userCode = principal.getUserVo().getUsercode();
		System.out.println("Fridge Contrller MyFridgeBox 진입!!!");
		FridgeVo fridgeVo = fridgeService.addFridge(authentication); //service에서 만든 값
//		fridgeService.addFridge(authentication, userVo,fridgeVo2);
		fridgeVo2 = fridgeService.userfridge(fridgeVo.getOwner()); //insert해서 가져오려고 함
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		System.out.println("FridgeVO.getCode22 : " + fridgeVo);
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		Integer fridgeCode = fridgeVo2.get(0).getCode();
		
		userService.addUserFridgeCode(fridgeCode, userCode);
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
	
	
	
	//수정하기 버튼 누르면 수정되게 만들어야 된다.
//	@PostMapping("/updateFridge")
//	public String updateFridgeBox(Integer ingredientcode,Integer quantity,Integer fridgecode) {
//		fridgeBoxVo.setFridgecode(5);
//		fridgeBoxVo.setIngredientcode(Integer.parseInt(ingredientcode));
//		fridgeBoxVo.setQuantity(Integer.parseInt(quantity));
//		System.out.println("FridgeController updateFridge진입");
//		System.out.println("setIngredientCode : " + fridgeBoxVo.getIngredientcode());
//		fridgeService.updateFridgeBox(fridgeBoxVo);
//		return "updateFridge";
//	}
	
	@PostMapping("/updateFridge")
	public String updateFridgeBox(Authentication authentication, Model model,FridgeBoxVo fridgeBoxVo) {
		fridgeBoxVo.setIngredientcode(261);
		fridgeBoxVo.setFridgecode(5);
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
		return "index";
	}
	

	
	
	
	@GetMapping("/")
	public String home(){
		return "index";
	}
	
}
