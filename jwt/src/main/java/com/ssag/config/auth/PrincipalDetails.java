package com.ssag.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ssag.model.UserVo;

public class PrincipalDetails implements UserDetails{
	
	private UserVo userVo;
	
	public PrincipalDetails(UserVo userVo) {
		this.userVo = userVo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		userVo.getRoleList().forEach(r ->{
			authorities.add(()-> r );
		});
		return authorities;
	}

	public UserVo getUserVo() {
		return userVo;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return userVo.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userVo.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	
	
}
