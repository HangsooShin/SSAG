package com.ssag.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssag.config.auth.PrincipalDetails;
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

	// 새로운 글 작성
	@GetMapping("/newArticle")
	public String writeArticle(Model model) {
		return "newArticle";
	}

	// 글 작성에서 등록하기 버튼 누르ㅏ면
	@PostMapping(value = "/addArticle")
	@ResponseBody
	public List<FridgeBoardVo>  addArticle(Model model, FridgeBoardVo fridgeBoardVo,
			@AuthenticationPrincipal PrincipalDetails details) {
		logger.info("메모 등록");
		fridgeBoardVo.setFridgecode(details.getUser().getFridgecode());
		System.out.println("usercode :"+details.getUser().getUsercode());
		fridgeBoardVo.setWriter(details.getUser().getUsercode());
		System.out.println(fridgeBoardVo.getMemotext());
		userService.addMemo(fridgeBoardVo);
		List<FridgeBoardVo> memoList = userService.memoList(details.getUser().getFridgecode(), details.getUser().getUsercode());
		return memoList;
	}

	@PostMapping(value = "/removeArticle")
	@ResponseBody
	public List<FridgeBoardVo>  removeArticle(@RequestParam("memocode") int memocode,@AuthenticationPrincipal PrincipalDetails details) {
		System.out.println("memocode !! : " + memocode);
		userService.deleteMemo(memocode);
		List<FridgeBoardVo> memoList = userService.memoList(details.getUser().getFridgecode(), details.getUser().getUsercode());
		return memoList;
	}

	// 글 목록 보여주는 곳
	@GetMapping("/appmemo")
	public String getMemo(Model model, @AuthenticationPrincipal PrincipalDetails daDetails) {
		System.out.println("=============== 앱 메모페이지 ===============");
		List<FridgeBoardVo> memoList = userService.memoList(daDetails.getUser().getFridgecode(),
				daDetails.getUser().getUsercode());
		model.addAttribute("memoList", memoList);
		return "/appmemo";
	}

	@PostMapping("/appaddmemo")
	@ResponseBody
	public void appaddmemo(Model model, String memotext, @AuthenticationPrincipal PrincipalDetails daDetails) {
		System.out.println("=============== 앱 메모 추가 ===============");

		FridgeBoardVo fridgeBoardVo = new FridgeBoardVo();
		fridgeBoardVo.setFridgecode(daDetails.getUser().getFridgecode());
		fridgeBoardVo.setWriter(daDetails.getUser().getUsercode());
		fridgeBoardVo.setMemotext(memotext);

		userService.addMemo(fridgeBoardVo);
	}

	@PostMapping("/appdeletememo")
	@ResponseBody
	public void appdeletememo(Model model, Integer memocode, @AuthenticationPrincipal UserVo user) {
		System.out.println("=============== 앱 메모 삭제 ===============");
		userService.deleteMemo(memocode);
	}

}