package com.ssag.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.ssag.model.UserVo;

@Mapper
@Repository("userDao")
public interface UserDao {
	
	public void insertUser(UserVo userVo) throws DataAccessException;
	public UserVo findByUsername(String username) throws DataAccessException;
//	CustomUserDetails getUserById(String id) throws DataAccessException;
	public void insertUserFridgeCode(String fridgecode, Integer code)throws DataAccessException;
	public UserVo updateUser(String username, Integer usercode)throws DataAccessException;
	public void procedureCall()throws DataAccessException;
	
}
