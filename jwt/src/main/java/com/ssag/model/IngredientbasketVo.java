package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component("ingredientbasketVo")
public class IngredientbasketVo {

	private Integer usercode;
	private Integer ingredientcode;
	private Integer ingredientquantityinbasket;
	private Integer cookcode;
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public Integer getIngredientcode() {
		return ingredientcode;
	}
	public void setIngredientcode(Integer ingredientcode) {
		this.ingredientcode = ingredientcode;
	}
	public Integer getIngredientquantityinbasket() {
		return ingredientquantityinbasket;
	}
	public void setIngredientquantityinbasket(Integer ingredientquantityinbasket) {
		this.ingredientquantityinbasket = ingredientquantityinbasket;
	}
	public Integer getCookcode() {
		return cookcode;
	}
	public void setCookcode(Integer cookcode) {
		this.cookcode = cookcode;
	}
	
	

	
	
}
