package com.MyMall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/*
 * 返回前端的信息的通用类
 * */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable{
	private int status;
	private String msg;
	private T data;
	
	public int getStatus() {
		return status;
	}
	public String getMsg() {
		return msg;
	}
	public T getData() {
		return data;
	}
	private ServerResponse(int status,String msg,  T data) {
		super();
		this.msg = msg;
		this.status = status;
		this.data = data;
	}
	private ServerResponse(int status, T data) {
		super();
		this.status = status;
		this.data = data;
	}
	private ServerResponse(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
	private ServerResponse(int status) {
		super();
		this.status = status;
	}
	//判断是否成功
	@JsonIgnore
	public boolean IsSuccess() {
		return this.status==ResponseCode.SUCCESS.getCode();
	}
	
	public static <T>ServerResponse<T> CreateBySuccess(){
		return new ServerResponse(ResponseCode.SUCCESS.getCode());
		
	}
	public static  <T>ServerResponse<T> CreateBySuccessMsg(String msg){
		return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg);
		
	}
	public static <T>ServerResponse<T> CreateBySuccessMsgData(String msg,T data){
		return new ServerResponse(ResponseCode.SUCCESS.getCode(),msg,data);
	}
	public static <T>ServerResponse<T> CreateBySuccessData(T data){
		return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
	}
	public <T>ServerResponse<T> CreateByError(){
		return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
	}
	public static <T>ServerResponse<T> CreateByErrorMsg(String msg){
		return new ServerResponse(ResponseCode.ERROR.getCode(),msg);
	}
	public static <T>ServerResponse<T> CreateByErrorCodeMsg(int code,String msg){
		return new ServerResponse(code,msg);
	}
}
