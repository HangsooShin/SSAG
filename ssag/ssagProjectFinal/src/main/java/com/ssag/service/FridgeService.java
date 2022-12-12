package com.ssag.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.FridgeDao;
import com.ssag.dao.SearchDao;
import com.ssag.model.CookVo;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.IngredientbasketVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.StringSplitVo;
import com.ssag.model.UserVo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FridgeService {

	@Resource
	private FridgeDao fridgeDao;

	@Resource
	private SearchDao searchDao;
	
	
	@Autowired
	public FridgeService(FridgeDao fridgeDao) {
		this.fridgeDao = fridgeDao;
	}

	@Transactional
	public void createFridgeBox(FridgeBoxVo fridgeBoxVo) {
		System.out.println("fridgeService 진입 !! ==========================:  ");
		fridgeDao.insertItem(fridgeBoxVo);
		System.out.println("fridgeDao==========================:  " + fridgeDao);
	}

	@Transactional
	public List<IngredientVo> ingredientAll() {
		System.out.println("여기는 FridgeService");
		List<IngredientVo> ingredientList = fridgeDao.ingredientAll();

		ArrayList<IngredientVo> ingredientList2 = new ArrayList<IngredientVo>();
		for (int i = 0; i < ingredientList.size(); i++) {
			ingredientList2.add(ingredientList.get(i));
		}
		return ingredientList2;
	}


	//fridgecode가 없다면
	@Transactional
	public FridgeVo addFridge(@AuthenticationPrincipal PrincipalDetails principal,UserVo user){
		
		
//		System.out.println(details.getUsername());
//		System.out.println(user.getUsername());
//		
		
		
//		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
//		Integer userFridgeCode = principal.getUser().getFridgecode();
//		String fridgeCode = principal.getUser().getFridgecode();
//		Integer userCode = principal.getUser().getCode();
		
//		String fridgeCode = userVo.getFridgecode();
		String fridgeCode = principal.getUser().getFridgecode();
		System.out.println("이것도 널 아닌가?"+ fridgeCode);
//		Integer userCode = userVo.getUsercode();
		Integer userCode = principal.getUser().getUsercode();
		String username = principal.getUser().getUsername();
		System.out.println(username);
//		String fridgecodeCheck = fridgeDao.userfridgeList(fridgeCode);
		
//		System.out.println("FridgeCode  유무 : " + fridgecodeCheck);
		System.out.println("UserTable 의 GetCode" + userCode);
		
		if (fridgeCode == null) {
			System.err.println("Fridgecode 없음");
			FridgeVo fridgeVo = new FridgeVo();
			String fridgecode2 = UUID.randomUUID().toString();
			fridgeVo.setFridgeowner(userCode);
			fridgeVo.setFridgecreateddate(LocalDate.now());
			fridgeVo.setFridgename("내 냉장고");
			fridgeVo.setFridgecode(fridgecode2);
			fridgeDao.insertFridge(fridgeVo);
			//이렇게 되면 한번에 들어가는게 아닌가 ..?
//			userVo.setFridgecode(fridgecode2);
			user.setFridgecode(fridgecode2);
			
			System.out.println("FridgeService : fridgecode주입 진입");
//			fridgeDao.insertUserFridgeCode(fridgecode2, userVo.getUsercode());
			fridgeDao.insertUserFridgeCode(fridgecode2, principal.getUser().getUsercode());
//			System.out.println("inset문이 실행이 되는건가 ..? " + fridgecode2 + userVo.getUsercode());
			System.out.println("inset문 ?? : "+ principal.getUser().getUsercode());
			return fridgeVo;
		}
		System.out.println("FridgeCode 있음");
		System.out.println("FridgeService Fridge 이거 안나올 수 도 있다.: " + fridgeCode);
//		System.out.println("FridgeService Fridge 이거 안나올 수 도 있다.: " + fridgecodeCheck.getCode());
		return fridgeDao.userfridgeList(fridgeCode);
	}

//	public void addUserFridgeCode(String fridgecode, Integer code) {
//		userDao.insertUserFridgeCode(fridgecode, code);
//		System.out.println("userService : fridgecode주입 진입");
//	}
	
	
	public FridgeVo userfridge(String usercode) {
		FridgeVo userfridge = fridgeDao.userfridgeList(usercode);
		return userfridge;
	}

	@Transactional
	public StringSplitVo selectRecipeList(StringSplitVo stringSplitVo) {
		
		StringSplitVo container = fridgeDao.selectRecipeList(stringSplitVo);
//		container.setImglink(fridgeDao.selectRecipeList().get(1));
//		container.setLink(null);
//		container.setName(null);
		return container;
	}
	
	public void updateFridgeBox(FridgeBoxVo fridgeBoxVo) {
		System.out.println("FridgeService : updateFridgeBox 진입");
		fridgeDao.updateFridgeBox(fridgeBoxVo);
		System.out.println(fridgeBoxVo.getIngredientcode());
		System.out.println(fridgeBoxVo.getFridgecode());
	}
	
	public List<SimilarnameVo> getKeyword(String similar) {
		System.out.println("fridgeService 들어옴");
		System.out.println("fridgeService GetKeyword : " + similar);
		List<SimilarnameVo> similarnameList = searchDao.similarname(similar);
		System.out.println(similarnameList);//가져와 진다.
		return similarnameList;
	}
	
	public List<SimilarnameVo> procedure2(String similar){
		System.out.println("fridgeService : procedure2  들어옴");
		System.out.println("fridgeService procedure2 : " + similar);
		List<SimilarnameVo> procedureList = searchDao.recipeProcedureCall(similar);
//		System.out.println("여기가 프로시저 리스트 서비스임 : "+procedureList);
		return procedureList;
	}
	
	public List<CookVo> selectRecipe(String cookname){
		
		System.out.println("fridgeService : selectRecipe  들어옴");
		System.out.println("fridgeService selectRecipe : " + cookname);
		List<CookVo> recipeList = searchDao.selectRecipe(cookname);
		return recipeList;
	}
	
	public List<FridgeBoxVo> selectMyFridge(String userFridgeCode){
		System.out.println("fridgeService : myFridge  들어옴");
		System.out.println("fridgeService myFridge : " + userFridgeCode);
		List<FridgeBoxVo> fridgeBoxList = fridgeDao.myFridge(userFridgeCode);
		System.out.println("여기서 걸리나?"+fridgeBoxList);
		return fridgeBoxList;
		
	}
	
	//냉장고 재료 리스트(ALL)
	@Transactional
	public List<String> myFridgeBox(FridgeVo fridgeVo) {
		System.out.println("fridgeService MyfridgeBox!!!");
		List<FridgeVo> fridgeInfo = fridgeDao.myfridgeBox();
		List<String> fridgeInfoName = new ArrayList<String>();
		fridgeInfoName.add(fridgeInfo.get(1).getFridgename());
		return fridgeInfoName;

	}

	//식재료 추가
	public void insertbasket(IngredientbasketVo ingredientbasketVo) {
		fridgeDao.ingredientbasket(ingredientbasketVo);
	}

	
}

//	public List<> myFridgeList(UserVo userVo, FridgeVo fridgeVo)
