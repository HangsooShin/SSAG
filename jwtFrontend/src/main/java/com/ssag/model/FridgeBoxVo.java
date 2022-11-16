package com.ssag.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("fridgeboxVo")
public class FridgeBoxVo {

	private Integer ingredientcode;
	private String fridgecode;
	private Integer storagecode;
	private Integer quantity;
	

	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate createddate;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate expiredate;


	public Integer getIngredientcode() {
		return ingredientcode;
	}


	public void setIngredientcode(Integer ingredientcode) {
		this.ingredientcode = ingredientcode;
	}


	public String getFridgecode() {
		return fridgecode;
	}


	public void setFridgecode(String fridgecode) {
		this.fridgecode = fridgecode;
	}


	public Integer getStoragecode() {
		return storagecode;
	}


	public void setStoragecode(Integer storagecode) {
		this.storagecode = storagecode;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public LocalDate getCreateddate() {
		return createddate;
	}


	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}


	public LocalDate getExpiredate() {
		return expiredate;
	}


	public void setExpiredate(LocalDate expiredate) {
		this.expiredate = expiredate;
	}
	
	
//	private boolean userLogin;
//	
//	public FridgeBoxVo() {
//		this.userLogin = false;
//	}
	
}
