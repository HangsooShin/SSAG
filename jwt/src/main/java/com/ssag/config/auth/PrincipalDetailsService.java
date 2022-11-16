package com.ssag.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssag.dao.UserDao;
import com.ssag.model.UserVo;

import lombok.RequiredArgsConstructor;

//http://localhost:8080/login 요청이 올 때 동작을 한다.  => 여기서 동작을 안함 .formLogin().disable() 
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {
	
	private final UserDao userDao;
	
//	@Autowired
//	public PrincipalDetailsService(UserDao userDao) {
//		this.userDao = userDao;
//	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService 의 loadUserByUsername()");
		UserVo user = userDao.findByUsername(username);
		System.out.println("userVO : " + user);
		// TODO Auto-generated method stub
		return new PrincipalDetails(user);
	}

}
