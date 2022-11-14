package com.ssag.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("fridgeboxVo")
public class FridgeBoxVo {

	private Integer ingredientcode;
	private Integer fridgecode;
	private Integer storagecode;
	private Integer quantity;
	

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createddate;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiredate;
	
	
//	private boolean userLogin;
//	
//	public FridgeBoxVo() {
//		this.userLogin = false;
//	}
	
}
