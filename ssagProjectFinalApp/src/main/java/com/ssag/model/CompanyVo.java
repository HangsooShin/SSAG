package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.extern.apachecommons.CommonsLog;

@Component

public class CompanyVo {

	private Integer companycode;
	private String companylink;
	private String companyname;
	public Integer getCompanycode() {
		return companycode;
	}
	public void setCompanycode(Integer companycode) {
		this.companycode = companycode;
	}
	public String getCompanylink() {
		return companylink;
	}
	public void setCompanylink(String companylink) {
		this.companylink = companylink;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	

	
	
}
