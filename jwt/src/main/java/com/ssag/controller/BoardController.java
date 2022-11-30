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
	
//	//글 목록 보여주는 곳
//	List<FridgeBoardVo> memoList = new ArrayList<FridgeBoardVo>();
//	@GetMapping({"/listArticles"}) //주소를 여러개 맵핑 시킬 때 배열을 사용 "/" 이건 모든 경로 
//	public String getArticleList(Model model) {
//		logger.info("=============> getArticleList 메서드 진입");
//		memoList = userService.memoList();
//		model.addAttribute("memoList", memoList);
////		return "listArticles";
//		return null;
//	}
	
	//새로운 글 작성
	@GetMapping("/newArticle")
	public String writeArticle(Model model) {
//		return "articleForm";
		return "newArticle";
	}
//	
//	LocalTime now = LocalTime.now();
//	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//	String formatedNow = now.format(formatter);
//	

	
	//글 작성에서 등록하기 버튼 누르ㅏ면
	@PostMapping(value="/addArticle")
	public String addArticle(Model model,FridgeBoardVo fridgeBoardVo, @AuthenticationPrincipal UserVo userVo) {
		System.out.println("여기는 AddArticle");
//		fridgeBoardVo.setCreateddate(LocalTime.now());
		fridgeBoardVo.setFridgecode(userVo.getFridgecode());
		fridgeBoardVo.setWriter(userVo.getUsercode());
		System.out.println(fridgeBoardVo.getMemotext());
		userService.addMemo(fridgeBoardVo);
		
		return "redirect:myFridge";
	}
	
	@PostMapping(value="/removeArticle/{memocode}")
	public String removeArticle(@PathVariable("memocode") int memocode) {
		System.out.println("memocode !! : "+memocode);
//		System.out.println(memocode.TYPE);
		userService.deleteMemo(memocode);
//		boardService.removeArticle(Integer.parseInt(articleNo));
		return "redirect:/";
		
	}
}
