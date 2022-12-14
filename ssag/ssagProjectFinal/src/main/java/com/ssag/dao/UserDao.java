package com.ssag.dao;

import java.util.List;
import java.util.Optional;

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
	public List<FridgeBoardVo> memoList(String fridgecode) throws DataAccessException;
	public void insertMemo(FridgeBoardVo fridgeBoardVo) throws DataAccessException;
	public void deleteMemo(int memocode) throws DataAccessException;
	public Optional<UserVo> findByProviderAndProviderId(String provider,String providerId) throws DataAccessException;
	
	//이메일로 아이디 찾기
	public String find_id(String email) throws DataAccessException;
	//비밀번호 변경
	public int update_pw(UserVo userVo) throws Exception;
}
