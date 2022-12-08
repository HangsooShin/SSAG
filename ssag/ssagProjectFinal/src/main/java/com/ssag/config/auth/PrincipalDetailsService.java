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
public class PrincipalDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService 의 loadUserByUsername()");
		UserVo userVo = userDao.findByUsername(username);
		System.out.println("userVO : " + userVo);
		// TODO Auto-generated method stub
		System.out.println("UserDetails service의 USER : " +userVo);
		if(userVo == null) {
			return null;
		}else {
			System.out.println("이거 나오나");
			return new PrincipalDetails(userVo);
		}
	}

}
