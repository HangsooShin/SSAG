package com.ssag.sercurity.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.ssag.model.UserVo;

import lombok.Data;
import lombok.extern.java.Log;


//@Data
public class AccountContext extends User{
	
	  private UserVo userVo;

	  public AccountContext(UserVo userVo, List<GrantedAuthority> role) {
	    super(userVo.getUsername(), userVo.getPassword(), role);
	    this.userVo = userVo;
	  }

	public UserVo getUserVo() {
		return userVo;
	}

	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}
	
	

}
