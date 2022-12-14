package com.ssag.service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssag.dao.UserDao;
import com.ssag.model.CompanyVo;
import com.ssag.model.FridgeBoardVo;
import com.ssag.model.UserVo;

import lombok.AllArgsConstructor;

@Service("userService")
@Transactional
@AllArgsConstructor
public class UserService {
	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;
	
	public void addUser(UserVo userVo) {
		userDao.insertUser(userVo);
	}
//	public void findByProviderAndProviderId(UserVo userVo) {
//		userDao.findByProviderAndProviderId(userVo);
//		System.out.println("userService 진입 Provider");
//	}

	public Optional<UserVo> findByProviderAndProviderId(String provider, String providerId) {

		Optional<UserVo> providerInfo = userDao.findByProviderAndProviderId(provider, providerId);
		System.out.print("이건 뭘로 나오나 userservice" + userDao.findByProviderAndProviderId(provider, providerId));
		return providerInfo;
	}

	public UserVo findById(String username) {
		return userDao.findByUsername(username);
	}

	public void updateUser(UserVo userVo) {
		userDao.updateUser(userVo);
	}

	public void procedureCall() {
		userDao.procedureCall();
	}

	public List<CompanyVo> companyList() {
		System.out.println("여기는 UserService");
		List<CompanyVo> companyList = userDao.companyList();
		ArrayList<CompanyVo> companyServiceList = new ArrayList<CompanyVo>();
		for (int i = 0; i < companyList.size(); i++) {
			companyServiceList.add(companyList.get(i));
		}
		return companyServiceList;
	}

	public List<FridgeBoardVo> memoList(String fridgecode) {
		List<FridgeBoardVo> fridgeBoardVo = userDao.memoList(fridgecode);
		return fridgeBoardVo;
	}

	public void addMemo(FridgeBoardVo fridgeBoardVo) {
		System.out.println("여기는 Fridge Addmemo");
		userDao.insertMemo(fridgeBoardVo);
	}

	public void deleteMemo(int memocode) {
		System.out.println("여기는 Fridge deleteMemo");
		userDao.deleteMemo(memocode);
	}

	// 아이디 찾기
	public String find_id(HttpServletResponse response, String email) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String id = userDao.find_id(email);

		if (id == null) {
			out.println("<script>");
			out.println("alert('가입된 아이디가 없습니다.');");
			out.println("history.go(-1);");
			out.println("</script>");
			out.close();
			return null;
		} else {
			return id;
		}
	}

	// 비밀번호 변경

	public void send_mail(String password,UserVo userVo, String div) throws Exception {
		// Mail Server 설정
		String charSet = "utf-8";
		String hostSMTP = "smtp.naver.com";

		String hostSMTPid = "아이디";
		String hostSMTPpwd = "비밀번호";
		
		
		// 보내는 사람 EMail, 제목, 내용
		String fromEmail = "gongahze@naver.com";
		String fromName = "gongahze@naver.com";
		String subject = "";
		String msg = "";

		 if (div.equals("find_pw")) {
			subject = "Spring Homepage 임시 비밀번호 입니다.";
			msg += "<div align='center' style='border:1px solid black; font-family:verdana'>";
			msg += "<h3 style='color: blue;'>";
			msg += userVo.getUsername() + "님의 임시 비밀번호 입니다. 비밀번호를 변경하여 사용하세요.</h3>";
			msg += "<p>임시 비밀번호 : ";
			msg += password + "</p></div>";
		}
		// 받는 사람 E-Mail 주소
		String mail = userVo.getEmail();
		try {
			HtmlEmail email = new HtmlEmail();
			email.setDebug(true);
			email.setCharset(charSet);
			email.setSSL(true);
			email.setHostName(hostSMTP);
			email.setSmtpPort(587);

			email.setAuthentication(hostSMTPid, hostSMTPpwd);
			email.setTLS(true);
			email.addTo(mail, charSet);
			email.setFrom(fromEmail, fromName, charSet);
			email.setSubject(subject);
			email.setHtmlMsg(msg);
			email.send();
		} catch (Exception e) {
			System.out.println("메일발송 실패 : " + e);
		}
	}

	// 비밀번호 찾기
	public void find_pw(HttpServletResponse response, UserVo userVo) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println("입력받은 아이디"+userVo.getUsername());
		// 아이디가 없으면
		if (userDao.findByUsername(userVo.getUsername()) == null) {
			out.print("아이디가 없습니다.");
			out.close();
		}
		// 가입에 사용한 이메일이 아니면
		else if (!userVo.getEmail().equals(findById(userVo.getUsername()).getEmail())) {
			out.print("잘못된 이메일 입니다.");
			out.close();
		} else {
			// 임시 비밀번호 생성
			String password = "";

			 for (int i = 0; i < 12; i++) {
				 password += (char) ((Math.random() * 26) + 97);
				}
			System.out.println("임시 발급 비빌번호 : "+password);
			userVo.setPassword(passwordEncoder.encode(password));
			// 비밀번호 변경
//			userVo.update_pw(userVo);
			userDao.update_pw(userVo);
			// 비밀번호 변경 메일 발송
			send_mail(password,userVo, "find_pw");
			out.print("이메일로 임시 비밀번호를 발송하였습니다.");
			out.close();
		}
	}
}
