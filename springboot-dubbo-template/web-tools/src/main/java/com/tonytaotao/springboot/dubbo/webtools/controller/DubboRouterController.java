package com.tonytaotao.springboot.dubbo.webtools.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@Configuration
@ConfigurationProperties(prefix = "dubbo", ignoreUnknownFields = true)
@RequestMapping(value = "/api")
public class DubboRouterController {
    /**
     * 注册中心配置
     */
    private Map<String, String> registry;

    @Autowired
    private RestTemplate restTemplate;



    /**
     * @param dubboRequest
     * @desc: dubbo接口请求路由
     * 业务对接无需开发controller
     * @return: java.lang.Object
     * @author: hbt
     */
    @RequestMapping(value = "/router2")
    @ResponseBody
    public Object router2(@RequestBody @Valid DubboRequestDemo dubboRequest, ServletRequest servletRequest) throws Exception {

        Object result = null;

        if ("rest".equals(dubboRequest.getRequestType())) {

            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String sourceUrl = request.getRequestURI();

            String method = request.getMethod();
            HttpMethod httpMethod = HttpMethod.resolve(method);
            MultiValueMap<String, String> headers = createRequestHeaders(request);
            byte[] requestBody = createRequestBody(request);
            long startTime = System.currentTimeMillis();
            String requestParams = new String(requestBody, "UTF-8");

            RequestEntity<byte[]> requestEntity = new RequestEntity<byte[]>(requestBody, headers, httpMethod, new URI(sourceUrl));
            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(requestEntity, byte[].class);

            String responseBody = new String(responseEntity.getBody(), "UTF-8");
            JSONObject jsonObject = JSON.parseObject(responseBody);
            result = new GlobalResult<>(jsonObject);

        } else if ("rpc".equals(dubboRequest.getRequestType())) {
            result = DubboReference.invoke(dubboRequest.getServiceClass(), dubboRequest.getMethod(), dubboRequest.getBody(), dubboRequest.getAddress(), dubboRequest.getVersion(), dubboRequest.getRequestIp());
        }

        if (result != null && result instanceof Map) {
            Map<Object, Object> resMap = (Map<Object, Object>) result;
            if (resMap.containsKey("code")) {
                return result;
            }
        }
        return new GlobalResult<>(result);
    }

    private MultiValueMap<String, String> createRequestHeaders(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for (String headerName : headerNames) {
            List<String> headerValues = Collections.list(request.getHeaders(headerName));
            for (String headerValue : headerValues) {
                headers.add(headerName, headerValue);
            }
        }
        headers.add("Keep-Alive", "timeout=30, max=1000");
        return headers;
    }

    private byte[] createRequestBody(HttpServletRequest request) throws IOException {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }

    public Map<String, String> getRegistry() {
        return registry;
    }

    public void setRegistry(Map<String, String> registry) {
        this.registry = registry;
    }
}
