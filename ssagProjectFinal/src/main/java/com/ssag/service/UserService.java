package com.ssag.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssag.dao.UserDao;
import com.ssag.model.CompanyVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.ImageVo;
import com.ssag.model.IngredientVo;
import com.ssag.model.UserVo;

@Service("userService")
@Transactional
public class UserService {

	private UserDao userDao;
	
	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	
	public void addUser(UserVo userVo) {
		userDao.insertUser(userVo);
		System.out.println("userService 진입");
	}

	
	public UserVo findById(String username) {
		return userDao.findByUsername(username);
	}
	
	public void updateUser(UserVo userVo) {
		userDao.updateUser(userVo);
	}
	
	public void procedureCall() {
		userDao.procedureCall();
	}

	
	public List<CompanyVo> companyList() {
		System.out.println("여기는 UserService");
		List<CompanyVo> companyList = userDao.companyList();
		ArrayList<CompanyVo> companyServiceList = new ArrayList<CompanyVo>();
		for (int i = 0; i < companyList.size(); i++) {
			companyServiceList.add(companyList.get(i));
		}
		return companyServiceList;
	}
	
	public List<FridgeBoardVo> memoList(String fridgecode){
		List<FridgeBoardVo> fridgeBoardVo = userDao.memoList(fridgecode);
		return fridgeBoardVo;
	}
	
	public void addMemo(FridgeBoardVo fridgeBoardVo) {
		System.out.println("여기는 Fridge Addmemo");
		userDao.insertMemo(fridgeBoardVo);
	}
	
	public void deleteMemo(int memocode) {
		System.out.println("여기는 Fridge deleteMemo");
		userDao.deleteMemo(memocode);
	}
	

	
}
