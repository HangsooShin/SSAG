package com.ssag.sercurity.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssag.dao.UserDao;
import com.ssag.model.UserVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userDetailsService")
public class CustomUserDetailsSercive implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
//		Account account = userRepository.findById(id);s
		UserVo user = userDao.findByUsername(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("usernameNotFoundExcepttion");
		}
		
		
			
		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority(user.getRole()));
		
		AccountContext accountContext = new  AccountContext(user, roles);
		
		return accountContext;
	}

}
