package com.ssag.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.CookVo;
import com.ssag.model.CookbasketVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.SimilarnameVo;

@Mapper
@Repository("searchDao")
public interface SearchDao {

  // public SimilarnameVo productKeyword(String similar) throws
  // DataAccessException;

  public List<SimilarnameVo> similarname(String similar) throws DataAccessException;

  public List<SimilarnameVo> recipeProcedureCall(String similar) throws DataAccessException;

  public List<SimilarnameVo> recipeSearchbyname(String similar) throws DataAccessException;

  public List<CookVo> selectRecipe(String name) throws DataAccessException;

  public void getcookbasket(Integer cookquantityinbasket, Integer usercode, Integer cookcode)
      throws DataAccessException;

  public void updatecookbasket(Integer cookquantityinbasket, Integer usercode, Integer cookcode)
      throws DataAccessException;

  public List<CookbasketVo> findbasket(Integer usercode, Integer cookcode) throws DataAccessException;

  public List<CookbasketVo> cookbasketlist(Integer usercode, String fridgecode) throws DataAccessException;

  public void updatecookbasket2(Integer cookquantityinbasket, Integer usercode, Integer cookcode);

  public void deletecookbasket2(Integer usercode, Integer cookcode);

  public List<IngredientVo> ingredientchecklist(HashMap<String, Object> data);
}
