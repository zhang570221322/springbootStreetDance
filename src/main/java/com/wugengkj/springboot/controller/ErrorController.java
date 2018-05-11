package com.wugengkj.springboot.controller;


import com.wugengkj.springboot.common.enums.ErrorStatus;
import com.wugengkj.springboot.common.exception.GlobalException;
import com.wugengkj.springboot.common.vo.ErrorTemplateVO;
import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpMethod;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

/**
 * 错误处理
 *
 * @author leaf
 * <p>date: 2018-05-07 19:55</p>
 * <p>version: 1.0</p>
 */
@Api(value = "系统错误请求", description = "/error")
@RestControllerAdvice
@RequestMapping("error")
@Slf4j
public class ErrorController extends AbstractErrorController {
    private ErrorAttributes attributes;

    @Autowired
    public ErrorController(ErrorAttributes attributes) {
        super(attributes);
        this.attributes = attributes;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @ApiOperation(value = "系统内部错误")
    @RequestMapping
    public ResponseInfoVO globalException(HttpServletRequest request) {
        ErrorTemplateVO template;
        // 获取系统自定义错误参数
        Map<String, Object> errorAttributes = this.getErrorAttributes(request, false);
        // 获取自定义异常参数
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        Throwable exception = attributes.getError(requestAttributes).getCause();
        if (exception == null) {
            return ResponseInfoVO.fail(ErrorTemplateVO.builder()
                    .code(errorAttributes.get("status").toString())
                    .error(errorAttributes.get("error").toString())
                    .message(errorAttributes.get("message").toString())
                    .exception(errorAttributes.get("exception").toString())
                    .path(errorAttributes.get("path").toString())
                    .build());
        }

        if (exception instanceof GlobalException) {
            // 转换为自定义错误异常
            ErrorStatus status = ((GlobalException) exception).getStatus();
            template = ErrorTemplateVO.builder()
                    .error(status.getError())
                    .code(status.getCode())
                    .message(status.getMessage())
                    .exception(exception.getClass().getName())
                    .path(errorAttributes.get("path").toString())
                    .build();
        } else {
            // 转换为未识别异常
            template = ErrorTemplateVO.builder()
                    .error(ErrorStatus.GLOBAL_ERROR.getError())
                    .code(ErrorStatus.GLOBAL_ERROR.getCode())
                    .message(ErrorStatus.GLOBAL_ERROR.getMessage())
                    .exception(exception.getClass().getName())
                    .path(errorAttributes.get("path").toString())
                    .build();
        }

        return ResponseInfoVO.fail(template);
    }


    @ApiOperation(value = "HTTP请求方法错误")
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @GetMapping("no_match_method_type")
    public ResponseInfoVO httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        Set<HttpMethod> supportedHttpMethods = e.getSupportedHttpMethods();
        HttpMethod next = supportedHttpMethods.iterator().next();
        return ResponseInfoVO.fail(new ErrorTemplateVO(ErrorStatus.HTTP_METHOD_ERROR, e.getClass().getName(), next.name()));
    }

    @ApiOperation(value = "缺少请求参数")
    @ExceptionHandler({MissingServletRequestParameterException.class})
    @GetMapping("miss_params")
    public ResponseInfoVO missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseInfoVO.fail(new ErrorTemplateVO(ErrorStatus.MISS_PARAM_ERROR, e.getClass().getName(), e.getParameterName()));
    }

    @ApiOperation(value = "简单参数验证无效")
    @GetMapping("invalid_param")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseInfoVO constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        ConstraintViolation<?> next = constraintViolations.iterator().next();
        return ResponseInfoVO.fail(new ErrorTemplateVO(ErrorStatus.PARAM_INVALID_ERROR, e.getClass().getName(), next.getMessage()));
    }

    @ApiOperation(value = "复杂参数验证无效")
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @GetMapping("invalid_params")
    public ResponseInfoVO methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ObjectError next = e.getBindingResult().getAllErrors().iterator().next();
        return ResponseInfoVO.fail(new ErrorTemplateVO(ErrorStatus.PARAM_INVALID_ERROR, e.getClass().getName(), next.getDefaultMessage()));
    }

    @ApiOperation(value = "参数类型不匹配")
    @ExceptionHandler(TypeMismatchException.class)
    @GetMapping("no_match_params")
    public ResponseInfoVO typeMismatchException(TypeMismatchException e) {
        return ResponseInfoVO.fail(new ErrorTemplateVO(ErrorStatus.PARAM_NO_MATCH_ERROR, e.getClass().getName(), e.getPropertyName() + ":" + e.getRequiredType()));
    }
}
