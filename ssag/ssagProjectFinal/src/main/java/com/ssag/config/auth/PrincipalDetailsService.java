package com.ssag.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssag.model.UserVo;
import com.ssag.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PrincipalDetailsService implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVo user = userService.findById(username);
		System.out.println("여기 타나");
		if(user == null) {
			System.out.println("아이디 정보 없음");
			return null;
		}else {
			System.out.println("로그인 진행");
			System.out.println(user);
			return new PrincipalDetails(user);
		}
		
	}

}
