package com.quaero.quaerosmartplatform.handler;

import com.quaero.quaerosmartplatform.annotations.ResponseResult;
import com.quaero.quaerosmartplatform.domain.enumeration.ApiStyleEnum;
import com.quaero.quaerosmartplatform.domain.interceptor.ResponseResultInterceptor;
import com.quaero.quaerosmartplatform.domain.result.DefaultErrorResult;
import com.quaero.quaerosmartplatform.domain.result.PlatformResult;
import com.quaero.quaerosmartplatform.domain.result.Result;
import com.quaero.quaerosmartplatform.utils.JsonUtil;
import com.quaero.quaerosmartplatform.utils.RequestContextUtil;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @desc 接口响应体处理器
 *
 * @author wuhanzhang
 * @since 2020/11/14
 */
@ControllerAdvice
public class ResponseResultHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        HttpServletRequest request = RequestContextUtil.getRequest();
        ResponseResult responseResultAnn = (ResponseResult) request.getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);
        return responseResultAnn != null && !ApiStyleEnum.NONE.name().equalsIgnoreCase(request.getHeader("Api-Style"));
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ResponseResult responseResultAnn = (ResponseResult) RequestContextUtil.getRequest().getAttribute(ResponseResultInterceptor.RESPONSE_RESULT);

        Class<? extends Result> resultClazz = responseResultAnn.value();

        if (resultClazz.isAssignableFrom(PlatformResult.class)) {
            if (body instanceof DefaultErrorResult) {
                DefaultErrorResult defaultErrorResult = (DefaultErrorResult) body;
                return PlatformResult.builder()
                        .code(Integer.valueOf(defaultErrorResult.getCode()))
                        .msg(defaultErrorResult.getMessage())
                        .data(defaultErrorResult.getErrors())
                        .build();
            } else if (body instanceof String) {
                return JsonUtil.object2Json(PlatformResult.success(body));
            }

            return PlatformResult.success(body);
        }

        return body;
    }
}
