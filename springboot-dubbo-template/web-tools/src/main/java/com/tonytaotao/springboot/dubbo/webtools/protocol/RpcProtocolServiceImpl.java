package com.tonytaotao.springboot.dubbo.webtools.protocol;

import com.tonytaotao.springboot.dubbo.webtools.vo.DubboReference;
import com.tonytaotao.springboot.dubbo.webtools.vo.RequestInfo;
import org.springframework.stereotype.Service;

/**
 * @author tonytaotao
 */
@Service("rpcProtocolService")
public class RpcProtocolServiceImpl implements ProtocolService {

    @Override
    public Object execute(RequestInfo requestInfo, Object requestContext) {

        DubboReference dubboReference = requestInfo.getDubboReference();

        return DubboReferenceInvoke.invoke(dubboReference);

    }

}
