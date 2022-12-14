package com.ssag.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.IngredientbasketVo;
import com.ssag.model.StringSplitVo;

@Mapper
@Repository("fridgeDao")
public interface FridgeDao {
	//재료등록
	public void insertItem(FridgeBoxVo fridgeBoxVo) throws DataAccessException;
	//재료 리스트 보여줌(등록 모달창에서)
	public List<IngredientVo> ingredientAll() throws DataAccessException;
	//냉장고 재료 등록
	public void insertFridge(FridgeVo fridgeVo) throws DataAccessException;
	//findgeCode로 검색해서 일치하는 사용자의 냉장고 재료 모두 보여줌
	public FridgeVo userfridgeList(String usercode) throws DataAccessException;
	//레시피 검색
	public StringSplitVo selectRecipeList(StringSplitVo stringSplitVo) throws DataAccessException;
	//냉장고 재료 수정
	public void updateFridgeBox(FridgeBoxVo fridgeBoxVo)throws DataAccessException;
	
	//user table 에 fridgecode주입             냉장고 코드            사람코드 35~요런거
	public void insertUserFridgeCode(String fridgecode, Integer usercode)throws DataAccessException;
	
	//장바구니에 식재료 추가
	public void ingredientbasket(IngredientbasketVo ingredientbasketVo) throws DataAccessException;

	//List  내 냉장고 번호에 맞는 냉장고 찾아줌
	public List<FridgeBoxVo> myfridgeBox(String userFridgeCode) throws DataAccessException;

	public void changeItem(HashMap<String, FridgeBoxVo> fridgehashmap) throws DataAccessException;
	
	public void deleteItem(FridgeBoxVo fridgeBoxVo) throws DataAccessException;
	
	public List<FridgeBoxVo> myFridge(String userFridgeCode) throws DataAccessException;
	
	
	
}


