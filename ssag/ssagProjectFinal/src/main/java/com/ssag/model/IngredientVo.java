package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component("ingredientVo")
public class IngredientVo {

	private Integer ingredientcode;
	private String ingredientname;
	private Integer ingredientgroup;
	private String ingredienticonlocation;
	
	
	public Integer getIngredientcode() {
		return ingredientcode;
	}
	public void setIngredientcode(Integer ingredientcode) {
		this.ingredientcode = ingredientcode;
	}
	public String getIngredientname() {
		return ingredientname;
	}
	public void setIngredientname(String ingredientname) {
		this.ingredientname = ingredientname;
	}
	public Integer getIngredientgroup() {
		return ingredientgroup;
	}
	public void setIngredientgroup(Integer ingredientgroup) {
		this.ingredientgroup = ingredientgroup;
	}
	public String getIngredienticonlocation() {
		return ingredienticonlocation;
	}
	public void setIngredienticonlocation(String ingredienticonlocation) {
		this.ingredienticonlocation = ingredienticonlocation;
	}
	@Override
	public String toString() {
		return "IngredientVo [ingredientcode=" + ingredientcode + ", ingredientname=" + ingredientname
				+ ", ingredientgroup=" + ingredientgroup + ", ingredienticonlocation=" + ingredienticonlocation + "]";
	}
	
	
}
