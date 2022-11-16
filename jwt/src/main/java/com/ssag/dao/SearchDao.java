package com.ssag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.CookVo;
import com.ssag.model.MerchandiseVo;
import com.ssag.model.SimilarnameVo;

@Mapper
@Repository("searchDao")
public interface SearchDao {
	
//	public SimilarnameVo productKeyword(String similar) throws DataAccessException;

	public List<SimilarnameVo> similarname(String similar) throws DataAccessException;
	public List<SimilarnameVo> recipeProcedureCall(String similar) throws DataAccessException;
	public List<CookVo> selectRecipe(String name) throws DataAccessException;
}
