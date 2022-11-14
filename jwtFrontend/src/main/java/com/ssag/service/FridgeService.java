package com.ssag.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.FridgeDao;
import com.ssag.dao.SearchDao;
import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;
import com.ssag.model.StringSplitVo;
import com.ssag.model.UserVo;

import ch.qos.logback.classic.spi.STEUtil;
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

	@Transactional
	public List<String> myFridgeBox(FridgeVo fridgeVo) {
		System.out.println("fridgeService MyfridgeBox!!!");
		List<FridgeVo> fridgeInfo = fridgeDao.myfridgeBox();
		List<String> fridgeInfoName = new ArrayList<String>();
		fridgeInfoName.add(fridgeInfo.get(1).getName());
		return fridgeInfoName;

	}

//	@Transactional
//	public void addFridge(FridgeVo fiFridgeVo) {
//		
//		fridgeDao.insertFridge(fiFridgeVo);
//		System.out.println("FridgeService AddFridge 진입");
//	}
	
	@Transactional
	public FridgeVo addFridge(Authentication authentication) {
		PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();

		String fridgeCode = principal.getUserVo().getFridgecode();

		Integer userCode = principal.getUserVo().getUsercode();

//		Integer userFridgeCode = principal.getUserVo().getFridgecode();

		System.out.println("FridgeCode 유무 : " + fridgeCode);
		System.out.println("UserTable 의 GetCode" + userCode);
		FridgeVo fridgeVo = new FridgeVo();
		if (fridgeCode == null) {
			System.err.println("Fridgecode 없음");

			fridgeVo.setOwner(userCode);
			fridgeVo.setCreateddate(LocalDate.now());
			fridgeVo.setName("내 냉장고");
			fridgeDao.insertFridge(fridgeVo);
//			userVo.setFridgecode(fridgeVo.getCode());
			return fridgeVo;
		}
		System.out.println("FridgeCode 있음");
		System.out.println("FridgeService Fridge 이거 안나올 수 도 있다.: " + fridgeCode);
		return null;
	}

	public List<FridgeVo> userfridge(Integer owner) {
		List<FridgeVo> userfridge = fridgeDao.userfridgeList(owner);
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
		return similarnameList;
	}
	
}

//	public List<> myFridgeList(UserVo userVo, FridgeVo fridgeVo)
