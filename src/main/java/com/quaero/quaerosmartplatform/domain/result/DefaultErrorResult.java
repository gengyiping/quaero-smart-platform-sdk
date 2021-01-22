package com.quaero.quaerosmartplatform.domain.result;


import com.quaero.quaerosmartplatform.domain.enumeration.BusinessExceptionEnum;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.utils.RequestContextUtil;
import com.quaero.quaerosmartplatform.utils.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * @desc 默认全局错误返回结果
 *       备注：该返回信息是spring boot的默认异常时返回结果{@link DefaultErrorAttributes}，目前也是我们服务的默认返回结果
 * 
 * @author zhumaer
 * @since 9/29/2017 3:00 PM
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DefaultErrorResult implements Result {

	/**
	 * HTTP响应状态码 {@link HttpStatus}
	 */
	private Integer status;

	/**
	 * HTTP响应状态码的英文提示
	 */
	private String error;

	/**
	 * 异常堆栈的精简信息
	 * 
	 */
	private String message;

	/**
	 * 我们系统内部自定义的返回值编码，{@link ResultCode} 它是对错误更加详细的编码
	 * 
	 * 备注：spring boot默认返回异常时，该字段为null
	 */
	private Integer code;

	/**
	 * 调用接口路径
	 */
	private String path;

	/**
	 * 异常的名字
	 */
	private String exception;

	/**
	 * 异常的错误传递的数据
	 */
	private Object errors;

	/**
	 * 时间戳
	 */
	private Date timestamp;

	public static DefaultErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus, Object errors) {
		DefaultErrorResult result = DefaultErrorResult.failure(resultCode, e, httpStatus);
		result.setErrors(errors);
		return result;
	}

	public static DefaultErrorResult failure(ResultCode resultCode, Throwable e, HttpStatus httpStatus) {
		DefaultErrorResult result = new DefaultErrorResult();
		result.setCode(resultCode.code());
		if (e instanceof BusinessException) {
			result.setMessage(e.getMessage());
		}else {
			result.setMessage(resultCode.message());
		}
		result.setStatus(httpStatus.value());
		result.setError(httpStatus.getReasonPhrase());
		result.setException(e.getClass().getName());
		String path = RequestContextUtil.getRequest().getRequestURI();
		result.setPath(path);
		result.setTimestamp(new Date());
		return result;
	}

	public static DefaultErrorResult failure(BusinessException e) {
		BusinessExceptionEnum ee = BusinessExceptionEnum.getByEClass(e.getClass());
		if (ee != null) {
			return DefaultErrorResult.failure(ee.getResultCode(), e, ee.getHttpStatus(), e.getData());
		}

		DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e.getResultCode() == null ? ResultCode.SUCCESS : e.getResultCode(), e, HttpStatus.OK, e.getData());
		if (StringUtil.isNotEmpty(e.getMessage())) {
			defaultErrorResult.setMessage(e.getMessage());
		}
		return defaultErrorResult;
	}

}
