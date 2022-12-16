package com.ssag.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void insertItem(FridgeBoxVo fridgeBoxVo) throws DataAccessException;
	public void changeItem(HashMap<String, FridgeBoxVo> fridgehashmap) throws DataAccessException;
	public void deleteItem(FridgeBoxVo fridgeBoxVo) throws DataAccessException;
	public List<IngredientVo> ingredientAll() throws DataAccessException;
//	public Map<String, Object> ingredientAll2() throws DataAccessException;
	public List<FridgeVo> myfridgeBox() throws DataAccessException;
	public void insertFridge(FridgeVo fridgeVo) throws DataAccessException;
	public FridgeVo userfridgeList(String usercode) throws DataAccessException;
	public StringSplitVo selectRecipeList(StringSplitVo stringSplitVo) throws DataAccessException;
	public void updateFridgeBox(FridgeBoxVo fridgeBoxVo)throws DataAccessException;
	public List<FridgeBoxVo> myFridge(String userFridgeCode) throws DataAccessException;
	
	//user table 에 fridgecode주입             냉장고 코드            사람코드 35~요런거
	public void insertUserFridgeCode(String fridgecode, Integer usercode)throws DataAccessException;
	
	//장바구니에 식재료 추가
	public void ingredientbasket(IngredientbasketVo ingredientbasketVo) throws DataAccessException;
}
