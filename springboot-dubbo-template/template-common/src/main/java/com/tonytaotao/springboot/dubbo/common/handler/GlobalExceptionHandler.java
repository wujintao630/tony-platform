package com.tonytaotao.springboot.dubbo.common.handler;

import com.tonytaotao.springboot.dubbo.common.base.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理全局异常（controller层抛出）
 * @author tonytaotao
 *
 * ControllerAdvice 和 RestControllerAdvice 区别：
 * ControllerAdvice注解后，方法上要同时存在 ExceptionHandler 和 ResponseBody 注解
 * RestControllerAdvice注解后，方法上只需要存在 ExceptionHandler 注解
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GlobalIDHandler.setId();
    }

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        log.error(GlobalIDHandler.getId(), e);

        StringBuilder errorMsg = new StringBuilder("请求参数校验异常：");
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String msg = allErrors.stream().map(objectError -> {
           return objectError instanceof FieldError ? "参数[" + ((FieldError)objectError).getField() + "] -> " + objectError.getDefaultMessage() : objectError.getDefaultMessage();
        }).collect(Collectors.joining(", "));
        errorMsg.append(msg).append(".");

        GlobalResult result = GlobalResult.DefaultFailure("500", errorMsg.toString());
        result.setRequestId(GlobalIDHandler.getId());

        return result;
    }

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalResult handleConstraintViolationException(ConstraintViolationException e) {

        log.error(GlobalIDHandler.getId(), e);

        StringBuilder errorMsg = new StringBuilder("请求参数校验异常：");

        String msg = e.getConstraintViolations().stream().map(constraintViolation -> {
            return constraintViolation.getPropertyPath() + " -> " + constraintViolation.getMessage();
        }).collect(Collectors.joining(", "));

        errorMsg.append(msg).append(".");

        GlobalResult result = GlobalResult.DefaultFailure("500", errorMsg.toString());
        result.setRequestId(GlobalIDHandler.getId());

        return result;
    }

    /**
     * 默认异常处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public GlobalResult defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.error(GlobalIDHandler.getId(), e);

        String errorMsg = "系统异常:" + e.getMessage();
        GlobalResult result = GlobalResult.DefaultFailure("500", errorMsg);
        result.setRequestId(GlobalIDHandler.getId());

        return result;
    }


}
