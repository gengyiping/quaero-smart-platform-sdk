package com.quaero.quaerosmartplatform.handler;


import com.quaero.quaerosmartplatform.domain.bo.ParameterInvalidItem;
import com.quaero.quaerosmartplatform.domain.enumeration.ResultCode;
import com.quaero.quaerosmartplatform.domain.result.DefaultErrorResult;
import com.quaero.quaerosmartplatform.exceptions.BusinessException;
import com.quaero.quaerosmartplatform.helper.ParameterInvalidItemHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * @desc 全局异常处理基础类
 *
 * @author wu hanzhang
 * @since 2020/11/4
 */
@Slf4j
public abstract class BaseGlobalExceptionHandler {

	/**
	 * 违反约束异常
	 */
	protected DefaultErrorResult handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
		log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
		List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertCVSetToParameterInvalidItemList(e.getConstraintViolations());
		return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
	}

	/**
	 * 处理验证参数封装错误时异常
	 */
	protected DefaultErrorResult handleConstraintViolationException(HttpMessageNotReadableException e, HttpServletRequest request) {
		log.info("handleConstraintViolationException start, uri:{}, caused by: ", request.getRequestURI(), e);
		return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 处理无权限错误时异常
	 */
	protected DefaultErrorResult handleNoAccessException(AccessDeniedException e, HttpServletRequest request) {
		log.info("AccessDeniedException start, uri:{}, caused by: ", request.getRequestURI(), e);
		return DefaultErrorResult.failure(ResultCode.PERMISSION_NO_ACCESS, e, HttpStatus.FORBIDDEN);
	}


	/**
	 * 处理参数绑定时异常（反400错误码）
	 */
	protected DefaultErrorResult handleBindException(BindException e, HttpServletRequest request) {
		log.info("handleBindException start, uri:{}, caused by: ", request.getRequestURI(), e);
		List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
		return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
	}

	/**
	 * 处理使用@Validated注解时，参数验证错误异常（反400错误码）
	 */
	protected DefaultErrorResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
		log.info("handleMethodArgumentNotValidException start, uri:{}, caused by: ", request.getRequestURI(), e);
		List<ParameterInvalidItem> parameterInvalidItemList = ParameterInvalidItemHelper.convertBindingResultToMapParameterInvalidItemList(e.getBindingResult());
		return DefaultErrorResult.failure(ResultCode.PARAM_IS_INVALID, e, HttpStatus.BAD_REQUEST, parameterInvalidItemList);
	}

	/**
	 * 处理通用自定义业务异常
	 */
	protected ResponseEntity<DefaultErrorResult> handleBusinessException(BusinessException e, HttpServletRequest request) {
		log.info("handleBusinessException start, uri:{}, exception:{}, caused by: {}", request.getRequestURI(), e.getClass(), e.getMessage());

		DefaultErrorResult defaultErrorResult = DefaultErrorResult.failure(e);
		return ResponseEntity
				.status(HttpStatus.valueOf(defaultErrorResult.getStatus()))
				.body(defaultErrorResult);
	}

	/**
	 * 处理未预测到的其他错误（反500错误码）
	 */
	protected DefaultErrorResult handleThrowable(Throwable e, HttpServletRequest request) {
		log.error("handleThrowable start, uri:{}, caused by: ", request.getRequestURI(), e);
		return DefaultErrorResult.failure(ResultCode.SYSTEM_INNER_ERROR, e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
