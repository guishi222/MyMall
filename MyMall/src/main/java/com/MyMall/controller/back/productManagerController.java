package com.MyMall.controller.back;

import com.MyMall.common.Const;
import com.MyMall.common.ResponseCode;
import com.MyMall.common.ServerResponse;
import com.MyMall.pojo.Product;
import com.MyMall.pojo.User;
import com.MyMall.service.IUserService;
import com.MyMall.service.IproductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/manage/product")
public class productManagerController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IproductService iproductService;
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdmin(user).IsSuccess()){
            return iproductService.saveOrUpdateProduct(product);
        }else{
            return ServerResponse.CreateByErrorMsg("没有权限");
        }
    }

    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdmin(user).IsSuccess()){
            return iproductService.setSaleStatus(productId,status);
        }else{
            return ServerResponse.CreateByErrorMsg("无权限操作");
        }
    }
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpSession session, Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.CreateByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,请登录管理员");

        }
        if(iUserService.checkAdmin(user).IsSuccess()){
            //填充业务
            return iproductService.manageProductDetail(productId);

        }else{
            return ServerResponse.CreateByErrorMsg("无权限操作");
        }
    }
}
