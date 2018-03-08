package com.MyMall.service.imp;


import com.MyMall.common.Const;
import com.MyMall.common.ServerResponse;
import com.MyMall.dao.UserMapper;
import com.MyMall.pojo.User;
import com.MyMall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service("iUserService")
public class UserServiceImp implements IUserService{

	private UserMapper userMapper;
	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	/**
	 * 检查用户登录
	 * */
	public ServerResponse<User> login(String username, String password) {
		int resultCount =userMapper.checkUserName(username);
		if (resultCount==0){
			return ServerResponse.CreateByErrorMsg("用户名不存在");
		}
		User user=userMapper.checkLogin(username, password);
		if (user==null) {
			return ServerResponse.CreateByErrorMsg("密码错误");
		}
		return ServerResponse.CreateBySuccessMsgData("登录成功",user);
	}
	/**
	 * 注册
	 * */
	public ServerResponse<String> register(User user) {
		int userCount=userMapper.checkUserName(user.getUsername());
		int emailCount=userMapper.checkEmail(user.getEmail());
		if (userCount>0) {
			return ServerResponse.CreateByErrorMsg("用户名已存在");
		}
		if (emailCount>0) {
			return ServerResponse.CreateByErrorMsg("邮箱已存在");
		}
		int insertCount=userMapper.insert(user);
		if (insertCount==0) {
			return ServerResponse.CreateByErrorMsg("注册失败");
		}
		user.setRole(Const.Role.Role_customer);
		return ServerResponse.CreateBySuccessMsg("注册成功");
	}
	/**
	 * 查询密码提示问题
	 * */
	public ServerResponse<String> GetQuestion(String username){
		int userCount=userMapper.checkUserName(username);
		if (userCount==0) {
			return ServerResponse.CreateByErrorMsg("用户名不存在");
		}
		String question=userMapper.getQuestion(username);
		if (question==null) {
			return ServerResponse.CreateByErrorMsg("密码提示问题为空");
		}
		return ServerResponse.CreateBySuccessData(question);
	}
	/**
	 * 检验密码提示问题答案
	 * */
	public ServerResponse<String> checkAnswer(String username,String question,String answer){
		int count=userMapper.checkAnwser(username, question, answer);
		if (count==0) {
			return ServerResponse.CreateByErrorMsg("答案错误");
		}
		return ServerResponse.CreateBySuccessMsg("答案正确");
	}
	/**
	 * 根据用户名修改密码
	 * */
	public ServerResponse<String> forgetResetPassword(String username,String password){
		int count=userMapper.updatePswByUsername(username, password);
		if (count==0) {
			return ServerResponse.CreateByErrorMsg("修改密码失败");
		}
		return ServerResponse.CreateBySuccessMsg("修改密码成功");
		
	}

	/* 
	 * 根据用户ID修改密码
	 */
	public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew) {
		int count=userMapper.checkPassword(user.getId(), passwordOld);
		if (count==0) {
			return ServerResponse.CreateByErrorMsg("密码错误");
		}
		int roleCount=userMapper.updatePswById(user.getId(), passwordNew);
		if (roleCount==0) {
			return ServerResponse.CreateByErrorMsg("修改密码失败");
		}
		
		return ServerResponse.CreateBySuccessMsg("修改密码成功");
	}
	/* 
	 * 更新用户信息
	 */
	public ServerResponse<User> updateUserInfo(User user) {
		int count=userMapper.checkEmail(user.getEmail());
		if (count>0) {
			return ServerResponse.CreateByErrorMsg("邮箱已存在");
		}
		User updateUser=new User();
		updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount=userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount==0) {
			return ServerResponse.CreateByErrorMsg("修改失败");
		}
		return ServerResponse.CreateBySuccessMsgData("修改成功", updateUser);
	}
	/**
	 * 检验是否为管理员
	 * */
	public ServerResponse<String> checkAdmin(User user){
		if (user!=null&&user.getRole().intValue()==Const.Role.Role_admin) {
			return ServerResponse.CreateBySuccess();
		}
		
		return ServerResponse.CreateByErrorMsg("该用户不是管理员");
	}
	
	
}
