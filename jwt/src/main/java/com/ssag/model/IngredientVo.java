package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.ToString;

@Component("ingredientVo")
@Data
public class IngredientVo {

	private Integer code;
	private String name;
	private Integer group;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGroup() {
		return group;
	}
	public void setGroup(Integer group) {
		this.group = group;
	}

	
	
	
}
