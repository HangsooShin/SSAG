package com.ssag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssag.dao.UserDao;
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

	public void addUserFridgeCode(Integer fridgecode, Integer code) {
		userDao.insertUserFridgeCode(fridgecode, code);
		System.out.println("userService : fridgecode주입 진입");
	}
	
	public UserVo findById(String username) {
		return userDao.findByUsername(username);
	}

	
	
}
