package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component("ingredientVo")
@Data
public class IngredientVo {

	private Integer ingredientcode;
	private String ingredientname;
	private Integer ingredientgroup;
	private String ingredienticonlocation;
	private Integer ingredientinfridge;
	private Integer ingrequantity;
	
	private Integer infridge;
	
	
	
}
