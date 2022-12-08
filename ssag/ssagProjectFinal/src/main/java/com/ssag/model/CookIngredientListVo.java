package com.ssag.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CookIngredientListVo {
	
	private CookVo cookVo;
	private List<IngredientVo> ingredientVoList;
	
	
	public CookVo getCookVo() {
		return cookVo;
	}
	public void setCookVo(CookVo cookVo) {
		this.cookVo = cookVo;
	}
	public List<IngredientVo> getIngredientVoList() {
		return ingredientVoList;
	}
	public void setIngredientVoList(List<IngredientVo> ingredientVoList) {
		this.ingredientVoList = ingredientVoList;
	}
	
	
	
	

}
