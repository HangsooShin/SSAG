package com.ssag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.FridgeBoxVo;
import com.ssag.model.FridgeVo;
import com.ssag.model.IngredientVo;

@Mapper
@Repository("fridgeDao")
public interface FridgeDao {
	
	public void insertItem(FridgeBoxVo fridgeBoxVo) throws DataAccessException;
	public List<IngredientVo> ingredientAll() throws DataAccessException;
//	public Map<String, Object> ingredientAll2() throws DataAccessException;
	public List<FridgeVo> myfridgeBox() throws DataAccessException;
	public void insertFridge(FridgeVo fridgeVo) throws DataAccessException;
	public List<FridgeVo> userfridgeList(Integer owner) throws DataAccessException;
	
	
}
