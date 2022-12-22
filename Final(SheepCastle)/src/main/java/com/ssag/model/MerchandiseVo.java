package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;


@Component("merchandiseVo")
public class MerchandiseVo {

	
	private int merchandisecode;
	
	private int companycode;
	
	private int ingredientcode;
	
	private String itemname;
	
	private int merchandisecost;
	
	private int outofstock;
	
	private String merchandiselink;
	
	private String merchandiseimglink;
	
	private IngredientVo ingredientVo;


	public IngredientVo getIngredientVo() {
		return ingredientVo;
	}

	public void setIngredientVo(IngredientVo ingredientVo) {
		this.ingredientVo = ingredientVo;
	}

	public int getMerchandisecode() {
		return merchandisecode;
	}

	public void setMerchandisecode(int merchandisecode) {
		this.merchandisecode = merchandisecode;
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

	public int getMerchandisecost() {
		return merchandisecost;
	}

	public void setMerchandisecost(int merchandisecost) {
		this.merchandisecost = merchandisecost;
	}

	public int getOutofstock() {
		return outofstock;
	}

	public void setOutofstock(int outofstock) {
		this.outofstock = outofstock;
	}

	public String getMerchandiselink() {
		return merchandiselink;
	}

	public void setMerchandiselink(String merchandiselink) {
		this.merchandiselink = merchandiselink;
	}

	public String getMerchandiseimglink() {
		return merchandiseimglink;
	}

	public void setMerchandiseimglink(String merchandiseimglink) {
		this.merchandiseimglink = merchandiseimglink;
	}

	@Override
	public String toString() {
		return "MerchandiseVo [merchandisecode=" + merchandisecode + ", companycode=" + companycode
				+ ", ingredientcode=" + ingredientcode + ", itemname=" + itemname + ", merchandisecost="
				+ merchandisecost + ", outofstock=" + outofstock + ", merchandiselink=" + merchandiselink
				+ ", merchandiseimglink=" + merchandiseimglink + "]";
	}

	
}
