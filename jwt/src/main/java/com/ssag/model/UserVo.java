package com.ssag.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;


@Component("userVo")
@AllArgsConstructor
public class UserVo implements Serializable{

	
	private Integer usercode;
	
	private String username;
	private String password;
	private String role;//USER,ADMIN
	private String name;
	private String email;
	private String telephone;
	private String address;
	private Integer companycode;
	private String detailcompanyname;
//	private Integer fridgecode;
	private String fridgecode;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	
	private boolean userLogin;
	
	public UserVo() {
		this.userLogin = false;
	}
	
	public List<String> getRoleList(){
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}

	public Integer getUsercode() {
		return usercode;
	}

	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getCompanycode() {
		return companycode;
	}

	public void setCompanycode(Integer companycode) {
		this.companycode = companycode;
	}

	public String getDetailcompanyname() {
		return detailcompanyname;
	}

	public void setDetailcompanyname(String detailcompanyname) {
		this.detailcompanyname = detailcompanyname;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birth) {
		this.birth = birth;
	}

	public boolean isUserLogin() {
		return userLogin;
	}

	public void setUserLogin(boolean userLogin) {
		this.userLogin = userLogin;
	}


	public UserVo(Integer usercode, String username, String password, String role, String name, String email,
			String telephone, String address, Integer companycode, String detailcompanyname, String fridgecode,
			LocalDate birth, boolean userLogin) {
		super();
		this.usercode = usercode;
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
		this.companycode = companycode;
		this.detailcompanyname = detailcompanyname;
		this.fridgecode = fridgecode;
		this.birth = birth;
		this.userLogin = userLogin;
	}

	public String getFridgecode() {
		return fridgecode;
	}

	public void setFridgecode(String fridgecode) {
		this.fridgecode = fridgecode;
	}


	
	
}
