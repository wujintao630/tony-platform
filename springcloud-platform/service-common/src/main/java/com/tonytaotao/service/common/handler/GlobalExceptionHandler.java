package com.tonytaotao.service.common.handler;

import com.tonytaotao.service.common.base.GlobalResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 处理全局异常（controller层抛出）
 * @author wujintao
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        com.tonytaotao.service.common.handler.GlobalIDHandler.setId();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public GlobalResult defaultErrorHandler(HttpServletRequest request, Exception e) {
        log.error(com.tonytaotao.service.common.handler.GlobalIDHandler.getId(), e);
        StringBuilder errorMsg = new StringBuilder("系统异常");

        // 参数不合法异常
        if (e instanceof MethodArgumentNotValidException) {
            List<ObjectError> allErrors = ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors();
            errorMsg.append("->参数异常：");
            for (ObjectError error : allErrors) {
                errorMsg.append("\n");
                if (error instanceof FieldError) {
                    errorMsg.append("field:").append(((FieldError)error).getField()).append(",");
                }
                errorMsg.append(error.getDefaultMessage());
            }
        } else {
            errorMsg.append(":").append(e.getMessage());
        }

        GlobalResult result = GlobalResult.DefaultFailure(errorMsg.toString());
        result.setCode("500");
        result.setRequestId(com.tonytaotao.service.common.handler.GlobalIDHandler.getId());

        return result;
    }


}
