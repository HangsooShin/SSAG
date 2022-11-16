package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("merchandiseVo")
public class MerchandiseVo {

	
	private int code;
	
	private int companycode;
	
	private int ingredientcode;
	
	private String itemname;
	
	private int cost;
	
	private int outofstock;
	
	private String link;
	
	private String imglink;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getCompanycode() {
		return companycode;
	}

	public void setCompanycode(int companycode) {
		this.companycode = companycode;
	}

	public int getIngredientcode() {
		return ingredientcode;
	}

	public void setIngredientcode(int ingredientcode) {
		this.ingredientcode = ingredientcode;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getOutofstock() {
		return outofstock;
	}

	public void setOutofstock(int outofstock) {
		this.outofstock = outofstock;
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

	
	
	
}
