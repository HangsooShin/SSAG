package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("cookIngredientVo")
public class CookIngredientVo {

	private Integer cookcode;
	private Integer ingredientcode;
	private Integer ingredientquantityincook;
	
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

	public Integer getIngredientquantityincook() {
		return ingredientquantityincook;
	}
	public void setIngredientquantityincook(Integer ingredientquantityincook) {
		this.ingredientquantityincook = ingredientquantityincook;
	}
	@Override
	public String toString() {
		return "CookIngredientVo [cookcode=" + cookcode + ", ingredientcode=" + ingredientcode
				+ ", ingredientquantityincook=" + ingredientquantityincook + "]";
	}

	
	
}
