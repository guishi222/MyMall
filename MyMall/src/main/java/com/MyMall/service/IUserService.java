package com.MyMall.service;
/**
 * user Service µÄ½Ó¿Ú
 * */

import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.User;

public interface IUserService {
	ServerResponse<User> login (String username,String password);
	ServerResponse<String> register(User user);
	ServerResponse<String> GetQuestion (String username);
	ServerResponse<String> checkAnswer (String username,String question,String answer);
	ServerResponse<String> forgetResetPassword(String username,String password);
	ServerResponse<String> resetPassword(User user,String passwordOld,String PasswordNew);
	ServerResponse<User> updateUserInfo(User user);
	ServerResponse<String> checkAdmin(User user);
}
