package com.quaero.quaerosmartplatform.domain.enumeration;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc API 统一返回状态码
 * 
 * @author zhumaer
 * @since 8/31/2017 3:00 PM
 */
public enum ResultCode {

	/* 成功状态码 */
	SUCCESS(200, "成功"),

	/* 参数错误*/
	PARAM_IS_INVALID(400, "参数无效"),
	PARAM_IS_BLANK(400, "参数为空"),
	PARAM_TYPE_BIND_ERROR(400, "参数类型错误"),
	PARAM_NOT_COMPLETE(400, "参数缺失"),

	/* 用户错误*/
	USER_NOT_LOGGED_IN(403, "用户未登录"),
	TOKEN_EXPIRATION(403, "用户Token过期"),
	PERMISSION_NO_ACCESS(401, "无访问权限"),
	USER_LOGIN_ERROR(401, "账号不存在或密码错误"),
	USER_ACCOUNT_FORBIDDEN(401, "账号已被禁用"),
	USER_NOT_EXIST(401, "用户不存在"),
	LOGIN_FAILED(401, "登录失败"),

	/* 业务错误*/
	SPECIFIED_QUESTIONED_USER_NOT_EXIST(400, "业务错误"),

	/* 系统错误*/
	SYSTEM_INNER_ERROR(500, "系统繁忙，请稍后重试"),

	/* 数据错误*/
	RESULT_DATA_NONE(400, "数据未找到"),
	DATA_IS_WRONG(400, "数据有误"),
	DATA_ALREADY_EXISTED(400, "数据已存在"),

	/* 接口错误*/
	INTERFACE_INNER_INVOKE_ERROR(500, "内部系统接口调用异常"),
	INTERFACE_OUTTER_INVOKE_ERROR(500, "外部系统接口调用异常"),
	INTERFACE_FORBID_VISIT(500, "该接口禁止访问"),
	INTERFACE_ADDRESS_INVALID(500, "接口地址无效"),
	INTERFACE_REQUEST_TIMEOUT(500, "接口请求超时"),
	INTERFACE_EXCEED_LOAD(500, "接口负载过高");

	private Integer code;

	private String message;

	ResultCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Integer code() {
		return this.code;
	}

	public String message() {
		return this.message;
	}

	public static String getMessage(String name) {
		for (ResultCode item : ResultCode.values()) {
			if (item.name().equals(name)) {
				return item.message;
			}
		}
		return name;
	}

	public static Integer getCode(String name) {
		for (ResultCode item : ResultCode.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return this.name();
	}

	/***
	 * 校验重复的code值
	 */
	static void main(String[] args) {
		ResultCode[] apiResultCodes = ResultCode.values();
		List<Integer> codeList = new ArrayList<Integer>();
		for (ResultCode apiResultCode : apiResultCodes) {
			if (codeList.contains(apiResultCode.code)) {
				System.out.println(apiResultCode.code);
			} else {
				codeList.add(apiResultCode.code());
			}

			System.out.println(apiResultCode.code() + " " + apiResultCode.message());
		}
	}
}
