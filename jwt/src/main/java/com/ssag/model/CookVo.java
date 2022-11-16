package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("cookVo")
public class CookVo {

	private Integer code;
	private String name;
	private Integer companycode;
	private Integer serve;
	private String howtomake;
	private String link;
	private String imglink;
	
	
}
