package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("cookIngredientVo")
public class CookIngredientVo {

	private Integer cookcode;
	private Integer ingredientcode;
	private Integer quantity;
	
}
