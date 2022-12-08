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
	private Integer ingredientquantityinfridgebox;
	private IngredientVo ingredientVo22;

	public IngredientVo getIngredientVo22() {
		return ingredientVo22;
	}

	public void setIngredientVo22(IngredientVo ingredientVo22) {
		this.ingredientVo22 = ingredientVo22;
	}

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String ingredientcreateddate;

//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String expiredate;

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


	public Integer getIngredientquantityinfridgebox() {
		return ingredientquantityinfridgebox;
	}

	public void setIngredientquantityinfridgebox(Integer ingredientquantityinfridgebox) {
		this.ingredientquantityinfridgebox = ingredientquantityinfridgebox;
	}

	public String getIngredientcreateddate() {
		return ingredientcreateddate;
	}

	public void setIngredientcreateddate(String ingredientcreateddate) {
		this.ingredientcreateddate = ingredientcreateddate;
	}

	public String getExpiredate() {
		return expiredate;
	}

	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}

//	private boolean userLogin;
//	
//	public FridgeBoxVo() {
//		this.userLogin = false;
//	}

}
