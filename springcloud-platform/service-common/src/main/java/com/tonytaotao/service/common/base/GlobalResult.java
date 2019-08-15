package com.tonytaotao.service.common.base;

import lombok.Data;

/**
 * 全局结果返回
 * @author wujintao
 */
@Data
public class GlobalResult<T> {

    private String requestId;

    private String code;

    private String msg;

    private T data;

    public GlobalResult() {
        this.code = "0";
        this.msg = "OK";
    }

    public GlobalResult(T data) {
        this.code = "0";
        this.msg = "OK";
        this.data = data;
    }

    public static GlobalResult DefaultSuccess() {
        return DefaultSuccess("OK");
    }

    public static GlobalResult DefaultSuccess(String msg) {
        GlobalResult globalResult = new GlobalResult();
        globalResult.setCode("0");
        globalResult.setMsg(msg);
        return globalResult;
    }

    public static GlobalResult DefaultFailure() {
       return DefaultFailure("系统异常");
    }

    public static GlobalResult DefaultFailure(String msg) {
        GlobalResult globalResult = new GlobalResult();
        globalResult.setCode("-1");
        globalResult.setMsg(msg);
        return globalResult;
    }
}
