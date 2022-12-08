package com.ssag.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ssag.model.FridgeBoardVo;
import com.ssag.model.UserVo;
import com.ssag.service.UserService;

@Controller
public class BoardController {
	
	
	private final UserService userService;
	private final FridgeBoardVo fridgeBoardVo;
	
	public BoardController(UserService userService, FridgeBoardVo fridgeBoardVo) {
		this.userService = userService;
		this.fridgeBoardVo = fridgeBoardVo;
	}
	
	Logger logger = LoggerFactory.getLogger("com.kong.controller.BoardController");
	
	//새로운 글 작성
	@GetMapping("/newArticle")
	public String writeArticle(Model model) {
		return "newArticle";
	}

	
	//글 작성에서 등록하기 버튼 누르ㅏ면
	@PostMapping(value="/addArticle")
	public String addArticle(Model model,FridgeBoardVo fridgeBoardVo, @AuthenticationPrincipal UserVo userVo) {
		System.out.println("여기는 AddArticle");
		fridgeBoardVo.setFridgecode(userVo.getFridgecode());
		fridgeBoardVo.setWriter(userVo.getUsercode());
		System.out.println(fridgeBoardVo.getMemotext());
		userService.addMemo(fridgeBoardVo);
		
		return "redirect:myFridge";
	}
	
	@PostMapping(value="/removeArticle/{memocode}")
	public String removeArticle(@PathVariable("memocode") int memocode) {
		System.out.println("memocode !! : "+memocode);
		userService.deleteMemo(memocode);
		return "redirect:/";
		
	}
}
