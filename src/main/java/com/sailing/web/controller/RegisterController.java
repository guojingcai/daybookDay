package com.sailing.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sailing.entity.User;
import com.sailing.service.UserService;
import com.sailing.util.MD5Util;
import com.sailing.util.Mail;
import com.sailing.util.MailUtil;
import com.sailing.util.ServiceException;

@Controller
public class RegisterController {
	@Autowired
	private UserService userservice;

	@RequestMapping("/register")
	public String insertUser(HttpServletRequest request,ModelMap mode, User user) throws ServiceException {
		String action = request.getParameter("action");

		if ("register".equals(action)) {
			User selectUser = userservice.selectUser(user.getUsername());
			if (selectUser == null) {
				Random random = new Random();
				String uid = random.nextInt(60000) % 30001 + 30000 + "";
				user.setUid(uid);
				user.setUpassword(MD5Util.encode2hex(user.getUpassword()));
				user.setUregistertime(new Date());
				user.setUvalidatecode(MD5Util.encode2hex(user.getUemail())); // 将邮箱做MD5加密后用作激活码
				user.setUstatus((byte) 0);
				userservice.insertUser(user);


				// 发送邮件
				try {
					regist(user);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mode.addAttribute("email",user.getUemail());
				return "verification/register_success";
			} else {
				throw new ServiceException("该邮箱已被使用！");
			}
		}
		return null;

	}
	
	@RequestMapping("/register/activation")
	public String selectUserCode(HttpServletRequest re,   String activate) throws ServiceException{
		User user = userservice.selectUserCode(activate);
		if(user !=null){
			 if(user.getUstatus()==0) {
				 Date currentTime= new Date();
				 if(currentTime.before(user.getLastActivateTime())){
					 user.setUstatus((byte) 1);
					 userservice.updateByPrimaryKeySelective(user);
					 return "verification/activate_success";
				 }else{
					 re.setAttribute("message" , "激活码已过期");
					 return "verification/activate_failure";
				 }
			 }
		}else{
			throw new ServiceException("该邮箱未注册（邮箱地址不存在）！");
		}
		return "";
	}
	public void regist(User user) throws Exception{
		 
		//发邮件
		Properties prop = new Properties();
		try{
			InputStream in = this.getClass().getClassLoader()
					.getResourceAsStream("email_template.properties");
			prop.load(in);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		String host = prop.getProperty("host");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		Session session = MailUtil.createSession(host, username, password);
		String from = prop.getProperty("from");
		String to = user.getUemail();
		
		String subject = prop.getProperty("subject");
		String content = MessageFormat.format(prop.getProperty("content"),
											  user.getUvalidatecode());
		Mail mail = new Mail(from,to,subject,content);
		try{
			MailUtil.send(session, mail);
		}catch(MessagingException e){
			throw new RuntimeException(e);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}
		
}
