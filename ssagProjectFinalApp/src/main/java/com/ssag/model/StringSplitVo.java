package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("stringSplitVo")
public class StringSplitVo {

	private String name;
	private String link;
	private String imglink;
	private String ingredientNameList;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImglink() {
		return imglink;
	}
	public void setImglink(String imglink) {
		this.imglink = imglink;
	}
	public String getIngredientNameList() {
		return ingredientNameList;
	}
	public void setIngredientNameList(String ingredientNameList) {
		this.ingredientNameList = ingredientNameList;
	}
	
	
	
}
