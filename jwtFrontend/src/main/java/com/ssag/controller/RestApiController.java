package com.ssag.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ssag.config.auth.PrincipalDetails;
import com.ssag.dao.UserDao;
import com.ssag.model.UserVo;
import com.ssag.service.UserService;

import lombok.RequiredArgsConstructor;

//@RestController
//@RequestMapping("api/v1")
@RequiredArgsConstructor
@Controller
public class RestApiController {

  @Autowired
  private UserDao userDao;

  @Autowired
  private UserService userService;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  // @GetMapping("/home")
  // @ResponseBody
  // public String home() {
  // return "<h1>home</h1>";
  // }

  @PostMapping("token")
  public String token() {
    return "<h1>token</h1>";
  }

  @PostMapping("/join")
  public String addUser(UserVo userVo) {
    // 소스는 사용자 정보가 담긴 accountDto 객체가 담긴 정보를 entity에 옮겨야 한다.
    userVo.setPassword(passwordEncoder.encode(userVo.getPassword()));
    userVo.setRole("ROLE_USER");
    // String fridgecode = UUID.randomUUID().toString();
    // userVo.setFridgecode(UUID.randomUUID().toString());

    // String uuid = UUID.randomUUID().toString();
    userService.addUser(userVo);

    System.out.println("USerVo22 =========================================" + userVo.getUsername());
    return "redirect:/";
  }

  @GetMapping("/join")
  public String createUser() {
    System.out.println("여기는 GET Mapping Users");
    return "register";
  }

  @GetMapping("api/v1/user")
  public String user(Authentication authentication, Model model, HttpServletRequest request,
      HttpServletResponse response) {
    // SecurityContextHolder.getContext().getAuthentication()
    PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
    System.out.println("principal : " + principal.getUserVo().getCode());
    System.out.println("principal : " + principal.getUserVo().getUsername());
    System.out.println("principal : " + principal.getUserVo().getPassword());
    System.out.println("principal : " + principal.getUserVo().getName());

    model.addAttribute("user", principal.getUserVo());

    return "api";
  }

  @GetMapping("/login")
  public String login() {

    return "login";
  }

  //
  @PostMapping("/login")
  public String login(String usernaem, String password) {
    return "index";
  }

  @GetMapping("whoareu")
  public String whoareu() {
    return "whoareu";
  }

  @GetMapping("/mypage")
  public String mypage(Model model) {
    Integer usercode = 10;

    // 지금 업데이트문을 실행하려면 findByUsercode로 해서 찾아와야 될 것 같은데 귀찮다.
    // Jwt토큰이 완성된다면 PrincipalDetails principal = (PrincipalDetails)
    // authentication.getPrincipal();
    // System.out.println("principal : " + principal.getUserVo().getUsercode());
    // 이런식으로 가져올 수 있으니 굳이 만들지 않겠다. !
    // user = userService.findById(id);
    model.addAttribute(usercode);

    // UserVo user = new UserVo();

    return "mypage";
  }

  @PostMapping("/mypage")
  public String updatePOST(String username, Integer usercode) throws Exception {

    userService.updateUser(username, usercode);
    return "redirect:/";
  }

  // 예시 insert 프로시저 호출가능 음 필요한 테이블 더 보고 완성형으로 해보면 될듯!!
  @GetMapping("/call")
  public String callProcedure() {
    userService.procedureCall();
    return "redirect:/";
  }

  // @PostMapping("/login")
  // public ResponseEntity<TokenResponse> login(@RequestBody UserVo userVo) {
  // String token = userService.createToken(userVo);
  // return ResponseEntity.ok().body(new TokenResponse(token, "bearer"));
  // }
}
