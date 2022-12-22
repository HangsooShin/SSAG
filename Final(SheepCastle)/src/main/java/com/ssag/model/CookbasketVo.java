package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
public class CookbasketVo {
	private Integer cookquantityinbasket;
	private Integer cookcode;
	private Integer ingredientcode;
	private Integer usercode;
	
	private CookVo cookVo;
	private IngredientVo ingredientVo;

	
	
	
}
