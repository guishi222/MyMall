package com.MyMall.controller.front;

import com.MyMall.common.Const;
import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.User;
import com.MyMall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	private IUserService iUserService;
	
	@Autowired
	public void setiUserService(IUserService iUserService) {
		this.iUserService = iUserService;
	}

   /**
    * 用户登录
    * */
	@RequestMapping(value="login.do",method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse<User> login (String username,String password,HttpSession session,Model model ) {
		ServerResponse<User> response=iUserService.login(username, password);
		if (response.IsSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		model.addAttribute(response);
		return response;
	}
	/**
	 * 用户登出
	 * */
	@RequestMapping(value="logout.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> logout(HttpSession session) {
		session.removeAttribute(Const.CURRENT_USER);
		return ServerResponse.CreateBySuccess();
	}
	
	@RequestMapping(value="register.do",method=RequestMethod.POST)
	@ResponseBody
	public ServerResponse<String> register(User user) {
		
		return iUserService.register(user);
	}
	/**
	 * 获取用户信息
	 * */
	@RequestMapping(value="get_user_info.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<User> GetUserInfo(HttpSession session){
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorMsg("当前用户未登录，请登录");
		}
		return ServerResponse.CreateBySuccessData(user);
	}
	/**
	 * 获取密码提示问题
	 * */
	@RequestMapping(value="get_question.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> GetQuestion(String username){
		
		return iUserService.GetQuestion(username);
	}
	@RequestMapping(value="check_answer.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> checkAnswer (String username,String question,String answer){
		ServerResponse<String> response=iUserService.checkAnswer(username, question, answer);
		if (response.IsSuccess()) {
			return response;
		}
		return response;
	}
	@RequestMapping(value="forget_reset_password.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> forgetResetPassword(String username,String password){
		
		return iUserService.forgetResetPassword(username, password);
	}
	@RequestMapping(value="reset_password.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String PasswordNew){
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorMsg("当前用户未登录，请登录");
		}
		return iUserService.resetPassword(user, passwordOld, PasswordNew);
	}
	@RequestMapping(value="update_user_info.do",method=RequestMethod.GET)
	@ResponseBody
	public ServerResponse<User> updateUserInfo(HttpSession session,User user){
		User courrentUser=(User) session.getAttribute(Const.CURRENT_USER);
		if (courrentUser==null) {
			return ServerResponse.CreateByErrorMsg("当前用户未登录，请登录");
		}
		user.setId(courrentUser.getId());
		user.setUsername(courrentUser.getUsername());
		ServerResponse response=iUserService.updateUserInfo(user);
		if (response.IsSuccess()) {
			session.setAttribute(Const.CURRENT_USER, response.getData());
		}
		return response;
		
	}

}
