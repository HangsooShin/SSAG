package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component("ingredientVo")
@Data
@ToString
public class IngredientVo {

	private Integer code;
	private String name;
	private Integer group;
	
}
