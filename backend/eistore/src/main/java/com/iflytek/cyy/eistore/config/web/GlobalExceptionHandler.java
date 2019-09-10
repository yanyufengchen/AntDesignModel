package com.iflytek.cyy.eistore.config.web;

import com.iflytek.lingxi.common.util.constant.DefaultErrorCode;
import com.iflytek.lingxi.common.util.exception.BaseCodeException;
import com.iflytek.lingxi.common.util.web.IError;
import com.iflytek.lingxi.common.util.web.Result;
import com.iflytek.lingxi.common.util.web.SimpleError;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.Set;

/**
 * 全局异常处理
 *
 * @author liuht
 * 2018/12/25 17:39
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    /**
     * 所有错误码异常统一处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler( {BaseCodeException.class})
    public ResponseEntity<Result> handleBaseCodeException(BaseCodeException ex) {
        return ResponseEntity.ok(Result.failure(ex.getErrorCode()));
    }

    /**
     * 登录认证处理
     *
     * @param ex AuthenticationException
     * @return
     */
    @ExceptionHandler( {AuthenticationException.class})
    public ResponseEntity<Result> handleAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.ok(Result.failure(DefaultErrorCode.AUTHC_ERROR));
    }

    /**
     * 参数验证 异常处理
     *
     * @param ex ConstraintViolationException
     * @return
     */
    @ExceptionHandler( {ConstraintViolationException.class})
    public ResponseEntity<Result> handleCiException(ConstraintViolationException ex) {
        final ResponseEntity<Result> result = ResponseEntity.ok(Result.failure(DefaultErrorCode.INVALID_PARAM));
        final Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            if (!StringUtils.isEmpty(violation.getMessage())) {
                final Result resultBody = result.getBody();
                assert resultBody != null;
                resultBody.setErrorMessage(violation.getMessage());
                break;
            }
        }
        return result;

    }

    /**
     * 兜底异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler( {Exception.class})
    public ResponseEntity<Result> handle(Exception ex) {
        IError iError = new SimpleError(DefaultErrorCode.SYS_ERROR.getCode(), DefaultErrorCode.SYS_ERROR.getError());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex instanceof HttpMessageNotReadableException
                || ex instanceof MethodArgumentTypeMismatchException) {
            iError.setError("参数解析失败");
            status = HttpStatus.BAD_REQUEST;
        } else if (ex instanceof HttpRequestMethodNotSupportedException) {
            iError.setError("不支持当前请求方法");
            status = HttpStatus.METHOD_NOT_ALLOWED;
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            iError.setError("不支持当前媒体类型");
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        } else if (ex instanceof SQLException) {
            iError.setError("服务运行SQL异常");
        } else if (ex instanceof MissingServletRequestParameterException) {
            status = HttpStatus.BAD_REQUEST;
            iError.setError("请求参数不全");
        } else if (ex instanceof MaxUploadSizeExceededException) {
            status = HttpStatus.PAYLOAD_TOO_LARGE;
            iError.setError("上传文件总大小不允许超过" + maxFileSize);
        } else if (ex instanceof MethodArgumentNotValidException) {
            status = HttpStatus.BAD_REQUEST;
            BindingResult bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            if (bindingResult.hasErrors()) {
                final FieldError fieldError = bindingResult.getFieldError();
                iError = new SimpleError(DefaultErrorCode.INVALID_PARAM.getCode(), fieldError.getDefaultMessage());
            }
        } else if (ex instanceof BindException) {
            BindingResult bindingResult = ((BindException) ex).getBindingResult();
            if (bindingResult.hasErrors()) {
                final FieldError fieldError = bindingResult.getFieldError();
                iError = new SimpleError(DefaultErrorCode.INVALID_PARAM.getCode(), fieldError.getDefaultMessage());
            }
        }
        return ResponseEntity.status(status)
                .body(Result.failure(iError));
    }

}
