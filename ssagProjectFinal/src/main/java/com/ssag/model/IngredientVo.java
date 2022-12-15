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
	private Integer ingredientinfridge;
	private Integer ingrequantity;
	
	private Integer infridge;
	
	public Integer getIngrequantity() {
		return ingrequantity;
	}
	public void setIngrequantity(Integer ingrequantity) {
		this.ingrequantity = ingrequantity;
	}
	public Integer getIngredientinfridge() {
		return ingredientinfridge;
	}
	public void setIngredientinfridge(Integer ingredientinfridge) {
		this.ingredientinfridge = ingredientinfridge;
	}
	public Integer getInfridge() {
		return infridge;
	}
	public void setInfridge(Integer infridge) {
		this.infridge = infridge;
	}
	
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
				+ ", ingredientgroup=" + ingredientgroup + ", ingredienticonlocation=" + ingredienticonlocation
				+ ", ingredientinfridge=" + ingredientinfridge + ", infridge=" + infridge + "]";
	}
	
	
	
}
