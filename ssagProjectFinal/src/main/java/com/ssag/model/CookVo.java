package com.ssag.model;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("cookVo")
public class CookVo {

	private Integer cookcode;
	private String cookname;
	private Integer companycode;
	private Integer serve;
	private String howtomake;
	private String cooklink;
	private String cookimglink;
	
	private IngredientVo ingredientVo;
	
	public Integer getCookcode() {
		return cookcode;
	}
	public void setCookcode(Integer cookcode) {
		this.cookcode = cookcode;
	}
	public String getCookname() {
		return cookname;
	}
	public void setCookname(String cookname) {
		this.cookname = cookname;
	}
	public Integer getCompanycode() {
		return companycode;
	}
	public void setCompanycode(Integer companycode) {
		this.companycode = companycode;
	}
	public Integer getServe() {
		return serve;
	}
	public void setServe(Integer serve) {
		this.serve = serve;
	}
	public String getHowtomake() {
		return howtomake;
	}
	public void setHowtomake(String howtomake) {
		this.howtomake = howtomake;
	}
	public String getCooklink() {
		return cooklink;
	}
	public void setCooklink(String cooklink) {
		this.cooklink = cooklink;
	}
	public String getCookimglink() {
		return cookimglink;
	}
	public void setCookimglink(String cookimglink) {
		this.cookimglink = cookimglink;
	}
	@Override
	public String toString() {
		return "CookVo [cookcode=" + cookcode + ", cookname=" + cookname + ", companycode=" + companycode + ", serve="
				+ serve + ", howtomake=" + howtomake + ", cooklink=" + cooklink + ", cookimglink=" + cookimglink + "]";
	}
	public IngredientVo getIngredientVo() {
		return ingredientVo;
	}
	public void setIngredientVo(IngredientVo ingredientVo) {
		this.ingredientVo = ingredientVo;
	}
	
	
	
}
