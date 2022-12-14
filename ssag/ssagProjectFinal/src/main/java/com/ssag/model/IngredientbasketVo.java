package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component("ingredientbasketVo")
public class IngredientbasketVo {

	private Integer usercode;
	private Integer ingredientcode;
	private Integer cookquantityinbasket;
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

	public Integer getCookcode() {
		return cookcode;
	}
	public void setCookcode(Integer cookcode) {
		this.cookcode = cookcode;
	}
	public Integer getCookquantityinbasket() {
		return cookquantityinbasket;
	}
	public void setCookquantityinbasket(Integer cookquantityinbasket) {
		this.cookquantityinbasket = cookquantityinbasket;
	}
	
	

	
	
}
