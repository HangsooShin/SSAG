package com.ssag.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class FridgeBoardVo {

	private String fridgecode;
	private int memocode;
	private Integer writer;
	private Integer imwriter;
	public Integer getImwriter() {
		return imwriter;
	}

	public void setImwriter(Integer imwriter) {
		this.imwriter = imwriter;
	}

	private String memotext;
	private String name;

//	 @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
//	private String createddate;

public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String memocreateddate;

	public String getFridgecode() {
		return fridgecode;
	}

	public void setFridgecode(String fridgecode) {
		this.fridgecode = fridgecode;
	}

	public int getMemocode() {
		return memocode;
	}

	public void setMemocode(int memocode) {
		this.memocode = memocode;
	}

	public Integer getWriter() {
		return writer;
	}

	public void setWriter(Integer writer) {
		this.writer = writer;
	}

	public String getMemotext() {
		return memotext;
	}

	public void setMemotext(String memotext) {
		this.memotext = memotext;
	}

	public String getMemocreateddate() {
		return memocreateddate;
	}

	public void setMemocreateddate(String memocreateddate) {
		this.memocreateddate = memocreateddate;
	}
	
}
