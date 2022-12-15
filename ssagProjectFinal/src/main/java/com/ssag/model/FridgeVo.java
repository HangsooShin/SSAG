package com.ssag.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component("fridgeVo")
public class FridgeVo {

	private Integer fridgeowner;
	private String fridgecode;
	private String fridgename;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate fridgecreateddate;

	public Integer getFridgeowner() {
		return fridgeowner;
	}

	public void setFridgeowner(Integer fridgeowner) {
		this.fridgeowner = fridgeowner;
	}

	public String getFridgecode() {
		return fridgecode;
	}

	public void setFridgecode(String fridgecode) {
		this.fridgecode = fridgecode;
	}

	public String getFridgename() {
		return fridgename;
	}

	public void setFridgename(String fridgename) {
		this.fridgename = fridgename;
	}

	public LocalDate getFridgecreateddate() {
		return fridgecreateddate;
	}

	public void setFridgecreateddate(LocalDate fridgecreateddate) {
		this.fridgecreateddate = fridgecreateddate;
	}

	

}
