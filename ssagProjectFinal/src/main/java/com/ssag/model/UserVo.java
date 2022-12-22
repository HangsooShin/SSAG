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
import lombok.Builder;
import lombok.Data;


@Component("userVo")
@AllArgsConstructor
@Data
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
	@Builder
	public UserVo(String username,String password,String address,String telephone,String email,String name, String role, String fridgecode,String detailcompanyname,Integer companycode) {
		this.username=username;
		this.password=password;
		this.address = address;
		this.telephone = telephone;
		this.name=name;
		this.role=role;
		this.fridgecode=fridgecode;
		this.detailcompanyname=detailcompanyname;
		this.companycode=companycode;
				
	}
	
	
}
