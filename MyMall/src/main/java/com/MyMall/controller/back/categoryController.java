package com.MyMall.controller.back;

import com.MyMall.common.Const;
import com.MyMall.common.ResponseCode;
import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.User;
import com.MyMall.service.IUserService;
import com.MyMall.service.IcategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
/**
 * 分类管理控制层
 * */
@Controller
@RequestMapping("/manage/category/")
public class categoryController {
	@Autowired
	private IUserService iuserService;
	@Autowired
	private IcategoryService icategoryService;
	/**
	 * 添加分类
	 * */
	@RequestMapping(value="add_category.do")
	@ResponseBody
	public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value="parentId",defaultValue="0")int parentId){
//		检查登录
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
		}
//		验证是否为管理员并执行业务逻辑
		if (iuserService.checkAdmin(user).IsSuccess()) {
			return icategoryService.addCategory(categoryName, parentId);
		}
		return iuserService.checkAdmin(user);
	}
	/**
	 * 修改分类名称
	 * */
	@RequestMapping(value="update_categoryName.do")
	@ResponseBody
	public ServerResponse updateCategoryName(HttpSession session,Integer id,String categoryName){
//		检查登录
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
		}
//		验证是否为管理员并执行业务逻辑
		if (iuserService.checkAdmin(user).IsSuccess()) {
			return icategoryService.updateCategoryName(id, categoryName);
		}
		return iuserService.checkAdmin(user);
	}
	/**
	 * 查询一级子分类
	 * */
	@RequestMapping(value="select_one_child.do")
	@ResponseBody
	public ServerResponse selectOneChild(HttpSession session,Integer categoryId){
//		检查登录
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
		}
//		验证是否为管理员并执行业务逻辑
		if (iuserService.checkAdmin(user).IsSuccess()) {
			return icategoryService.selectOneChild(categoryId);
		}
		return iuserService.checkAdmin(user);
	}
	/**
	* 查询所有子分类
	* */
	@RequestMapping(value="select_all_child.do")
	@ResponseBody
	public ServerResponse selectAllChild(HttpSession session,Integer categoryId) {
//		检查登录
		User user=(User) session.getAttribute(Const.CURRENT_USER);
		if (user==null) {
			return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(), "用户未登录");
		}
//		验证是否为管理员并执行业务逻辑
		if (iuserService.checkAdmin(user).IsSuccess()) {
			return icategoryService.selectChildAll(categoryId);
		}
		return iuserService.checkAdmin(user);
		
	}
}
