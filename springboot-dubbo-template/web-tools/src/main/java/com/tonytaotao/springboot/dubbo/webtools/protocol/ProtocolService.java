package com.tonytaotao.springboot.dubbo.webtools.protocol;

import com.tonytaotao.springboot.dubbo.webtools.vo.RequestInfo;

/**
 * @author tonytaotao
 */
public interface ProtocolService {

    Object execute(RequestInfo requestInfo, Object requestContext);

}
