package com.sailing.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sailing.entity.User;
import com.sailing.service.UserService;
import com.sailing.util.MD5Util;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired 
	private UserService userservice;
	 @RequestMapping("/checklogin")
	 @ResponseBody
	public String checklogin(HttpSession session ,String username,String password){
		String upassword=MD5Util.encode2hex(password);
		User selectLogin = userservice.selectLogin(username, upassword);
		if(selectLogin !=null){
			session.setAttribute("uid", selectLogin.getUid());
			return "success";
		}else{
			return "fail";
		}
	}
	@RequestMapping("/visitor")
	public String visitor(HttpServletRequest request){
		request.getSession().invalidate();
		return "/home1.1";
	}
	@RequestMapping("/home")
	public String home(HttpServletRequest request){
		 
		return "/home1.1";
	}
}
