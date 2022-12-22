package com.ssag.model;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CookbasketListVo {
	private Integer cookquantityinbasket;
	private CookVo cookVo;
	private List<IngredientVo> ingredientVoList;
	
	

	public Integer getCookquantityinbasket() {
		return cookquantityinbasket;
	}
	public void setCookquantityinbasket(Integer cookquantityinbasket) {
		this.cookquantityinbasket = cookquantityinbasket;
	}
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
