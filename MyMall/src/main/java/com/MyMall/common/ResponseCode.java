package com.MyMall.common;

/**
 * 返回前端信息的枚举
 * 
 * */
public enum ResponseCode {
	SUCCESS(0,"SUCCESS"),
	ERROR(1,"ERROR"),
	NEED_LOGIN(10,"NEED_LOGIN");
	private final int code;
	private final String desc;
	private ResponseCode(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public int getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
}
