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
import lombok.NoArgsConstructor;

@Builder
@Component("userVo")
@AllArgsConstructor
@Data
@NoArgsConstructor
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
	private String provider;
	private String providerId;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	
	private boolean userLogin;
	
	
	public List<String> getRoleList(){
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
//	@Builder
//	public UserVo(String username,String password,String address,String telephone,String email,String name, String role, String fridgecode,String detailcompanyname,Integer companycode) {
//		this.username=username;
//		this.password=password;
//		this.address = address;
//		this.telephone = telephone;
//		this.name=name;
//		this.role=role;
//		this.fridgecode=fridgecode;
//		this.detailcompanyname=detailcompanyname;
//		this.companycode=companycode;
//				
//	}

//	public UserVo(Integer usercode, String username, String password, String role, String name, String email,
//			String telephone, String address, Integer companycode, String detailcompanyname, String fridgecode,
//			LocalDate birth, boolean userLogin) {
//		super();
//		this.usercode = usercode;
//		this.username = username;
//		this.password = password;
//		this.role = role;
//		this.name = name;
//		this.email = email;
//		this.telephone = telephone;
//		this.address = address;
//		this.companycode = companycode;
//		this.detailcompanyname = detailcompanyname;
//		this.fridgecode = fridgecode;
//		this.birth = birth;
//		this.userLogin = userLogin;
//	}

	
}
