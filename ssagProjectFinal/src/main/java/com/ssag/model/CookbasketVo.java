package com.ssag.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CookbasketVo {
	private Integer usercode;
	private Integer cookcode;
	private Integer ingredientcode;
	private Integer cookquantityinbasket;
	
	private CookVo cookVo;
	private IngredientVo ingredientVo;
	
//	@Override
//	public String toString() {
//		return "CookbasketVo [cookquantityinbasket=" + cookquantityinbasket + ", cookVo=" + cookVo + ", ingredientVo="
//				+ ingredientVo + "]";
//	}
	
	public Integer getCookcode() {
		return cookcode;
	}
	public void setCookcode(Integer cookcode) {
		this.cookcode = cookcode;
	}
	public Integer getIngredientcode() {
		return ingredientcode;
	}
	public void setIngredientcode(Integer ingredientcode) {
		this.ingredientcode = ingredientcode;
	}
	public Integer getUsercode() {
		return usercode;
	}
	public void setUsercode(Integer usercode) {
		this.usercode = usercode;
	}
	public Integer getCookquantityinbasket() {
		return cookquantityinbasket;
	}
	public void setCookquantityinbasket(Integer cookquantityinbasket) {
		this.cookquantityinbasket = cookquantityinbasket;
	}
	public IngredientVo getIngredientVo() {
		return ingredientVo;
	}
	public void setIngredientVo(IngredientVo ingredient) {
		this.ingredientVo = ingredient;
	}
	public CookVo getCookVo() {
		return cookVo;
	}
	public void setCookVo(CookVo cookVo) {
		this.cookVo = cookVo;
	}
	
}
