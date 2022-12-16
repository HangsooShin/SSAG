package com.ssag.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

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

  // //글 목록 보여주는 곳
  // List<FridgeBoardVo> memoList = new ArrayList<FridgeBoardVo>();
  // @GetMapping({"/listArticles"}) //주소를 여러개 맵핑 시킬 때 배열을 사용 "/" 이건 모든 경로
  // public String getArticleList(Model model) {
  // logger.info("=============> getArticleList 메서드 진입");
  // memoList = userService.memoList();
  // model.addAttribute("memoList", memoList);
  //// return "listArticles";
  // return null;
  // }

  //
  // LocalTime now = LocalTime.now();
  // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd
  // HH:mm:ss");
  // String formatedNow = now.format(formatter);
  //

  // 글 작성에서 등록하기 버튼 누르ㅏ면
  @PostMapping(value = "/addArticle")
  @ResponseBody
  public List<FridgeBoardVo> addArticle(FridgeBoardVo fridgeBoardVo, @AuthenticationPrincipal UserVo user,
      String memotext) {
    System.out.println("여기는 AddArticle");
    // fridgeBoardVo.setCreateddate(LocalTime.now());
    fridgeBoardVo.setFridgecode(user.getFridgecode());
    fridgeBoardVo.setWriter(user.getUsercode());
    // System.out.println(fridgeBoardVo.getMemotext());
    fridgeBoardVo.setMemotext(memotext);
    userService.addMemo(fridgeBoardVo);
    List<FridgeBoardVo> memoList = userService.memoList(user.getFridgecode(), user.getUsercode());

    return memoList;
  }

  @PostMapping(value = "/removeArticle")
  @ResponseBody
  public List<FridgeBoardVo> removeArticle(@AuthenticationPrincipal UserVo user, int memocode) {
    System.out.println("memocode !! : " + memocode);
    // System.out.println(memocode.TYPE);
    userService.deleteMemo(memocode);
    List<FridgeBoardVo> memoList = userService.memoList(user.getFridgecode(), user.getUsercode());
    // boardService.removeArticle(Integer.parseInt(articleNo));
    return memoList;

  }

  // 글 목록 보여주는 곳
  @GetMapping("/appmemo")
  public String getMemo(Model model, @AuthenticationPrincipal UserVo user) {
    System.out.println("=============== 앱 메모페이지 ===============");
    List<FridgeBoardVo> memoList = userService.memoList(user.getFridgecode(), user.getUsercode());
    model.addAttribute("memoList", memoList);
    return "/appmemo";
  }

  @PostMapping("/appaddmemo")
  @ResponseBody
  public void appaddmemo(Model model, String memotext, @AuthenticationPrincipal UserVo user) {
    System.out.println("=============== 앱 메모 추가 ===============");

    FridgeBoardVo fridgeBoardVo = new FridgeBoardVo();
    fridgeBoardVo.setFridgecode(user.getFridgecode());
    fridgeBoardVo.setWriter(user.getUsercode());
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
