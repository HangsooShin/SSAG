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
import com.ssag.service.FridgeService;
import com.ssag.service.UserService;

@Controller
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
	Map<Integer, String> result = new HashMap<>();

	@PostMapping("/fridgeBox")
	public String createFridgeBox(FridgeBoxVo fridgeBoxVo, IngredientVo ingredientVo) {
		fridgeBoxVo.setIngredientcode(ingredientVo.getCode());
		fridgeBoxVo.setStoragecode(1);
		System.out
				.println("=====================================setIngredientcode : " + fridgeBoxVo.getIngredientcode());
		fridgeBoxVo.setCreateddate(LocalDate.now());
		fridgeBoxVo.setFridgecode(5);
		fridgeService.createFridgeBox(fridgeBoxVo);
		return "redirect:/";
	}

	@GetMapping("/fridgeBox")
	public String writeArticle(Model model) {
		System.out.println("여기는 controller : fridgeBox ");

		List<IngredientVo> ingredientList3 = fridgeService.ingredientAll();
		model.addAttribute("ingredientList3", ingredientList3);

//		userService.addUser(userVo);

		return null;
	}

//	@GetMapping("/myFridgeBox")
//	public String addFridge(Authentication authentication, UserVo userVo, FridgeVo fridgeVo) {
//		System.out.println("Fridge Contrller MyFridgeBox 진입!!!");
//
//		// user의 fridgecode 유무확인
//		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//		Integer fridgeCode = principal.getUserVo().getFridgecode();
//		Integer userCode = principal.getUserVo().getCode();
//		Integer userFridgeCode = principal.getUserVo().getFridgecode();
//		System.out.println("FridgeCode 유무 : " + fridgeCode);
//		System.out.println("UserTable 의 GetCode" + userCode);
//		if (fridgeCode == null) {
//			System.err.println("Fridgecode 없음");
//			fridgeVo.setOwner(userCode);
//			fridgeVo.setCreateddate(LocalDate.now());
//			fridgeVo.setName("내 냉장고");
//			fridgeService.addFridge(fridgeVo);
////			userVo.setFridgecode(fridgeVo.getCode());
//		}
//
//		else if (userFridgeCode == null) {
//			System.out.println("UserFridgeCode if문 ");
//			System.out.println("userVo getCode : " + fridgeVo.getCode());
//			userService.addUserFridgeCode(fridgeVo.getCode(), userCode);
//		} else {
//			System.out.println("FridgeCode 있음");
//			
//		}
//		return "fridgeBox";
//	}

	List<FridgeVo> fridgeVo2 = new ArrayList<FridgeVo>();
	@GetMapping("/myFridgeBox")
	public String addFridge(Authentication authentication) {
		
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
		Integer userCode = principal.getUserVo().getCode();
		System.out.println("Fridge Contrller MyFridgeBox 진입!!!");
		FridgeVo fridgeVo = fridgeService.addFridge(authentication); //service에서 만든 값
//		fridgeService.addFridge(authentication, userVo,fridgeVo2);
		fridgeVo2 = fridgeService.userfridge(fridgeVo.getOwner()); //insert해서 가져오려고 함
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		System.out.println("FridgeVO.getCode22 : " + fridgeVo);
		System.out.println("FridgeVO.getCode11 : " + fridgeVo2.get(0).getCode());
		Integer fridgeCode = fridgeVo2.get(0).getCode();
		
		userService.addUserFridgeCode(fridgeCode, userCode);
//commit 안되서 실험
		return "fridgeBox";
	}
}
