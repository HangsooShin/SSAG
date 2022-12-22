package com.ssag.model;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class FridgeBoardVo {

	private String fridgecode;
	private int memocode;
	private Integer writer;
	private String memotext;
	private String name;
	private Integer imwriter;
	
	//	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
	//	private String createddate;
	//	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private String memocreateddate;

	
}
