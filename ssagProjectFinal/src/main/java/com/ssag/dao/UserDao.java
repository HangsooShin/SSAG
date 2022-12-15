package com.ssag.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.CompanyVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.UserVo;

@Mapper
@Repository("userDao")
public interface UserDao {
	
	public void insertUser(UserVo userVo) throws DataAccessException;
	public UserVo findByUsername(String username) throws DataAccessException;

	public void updateUser(UserVo userVo)throws DataAccessException;
	public void procedureCall()throws DataAccessException;
	public List<CompanyVo> companyList()throws DataAccessException;
	public List<FridgeBoardVo> memoList(HashMap<String, Object> data) throws DataAccessException;
	public void insertMemo(FridgeBoardVo fridgeBoardVo) throws DataAccessException;
	public void deleteMemo(Integer memocode) throws DataAccessException;
}
