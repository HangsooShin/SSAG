package com.ssag.sercurity.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

@SuppressWarnings("serial")
public class FormWebAuthenticationDetails extends WebAuthenticationDetails{

	private String secretKey;

	public FormWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		secretKey = request.getParameter("secret_Key");
		System.out.println("secret_key ========================="+ secretKey);
		System.out.println("secret_key =========================" + request.getParameter("secret_key"));
		// TODO Auto-generated constructor stub
	}

	public String getSecretKey() {
		return secretKey;
	}

}
