package com.tonytaotao.springboot.dubbo.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class HttpLogFilter extends OncePerRequestFilter implements Ordered {

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 10;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (!checkFilter(httpServletRequest)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        // 因为请求body里字符的传输是通过HttpServletRequest中的字节流getInputStream()获得的，而这个字节流在读取了一次之后就不复存在了，
        // 所以需要本地存储传输流
        MyHttpServletRequestWrapper httpServletRequestWrapper = new MyHttpServletRequestWrapper(httpServletRequest);

        MyHttpServletResponseWrapper httpServletResponseWrapper = new MyHttpServletResponseWrapper(httpServletResponse);


        long startTime = System.currentTimeMillis();
        String ip = getIp(httpServletRequestWrapper);
        String path = httpServletRequestWrapper.getRequestURI();
        String header = getHttpHeader(httpServletRequestWrapper);
        String method = httpServletRequestWrapper.getMethod();
        String contentType = httpServletRequestWrapper.getContentType();
        String requestParamOrBody = JSON.toJSONString(getRequestParam(httpServletRequestWrapper));

        log.info("request -------- ip = {}, path = {}, header = {}, method = {}, contentType = {}, requestParamOrBody = {}", ip, path, header, method, contentType, requestParamOrBody);

        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();

        try {
            filterChain.doFilter(httpServletRequestWrapper, httpServletResponseWrapper);
            status = httpServletResponseWrapper.getStatus();
        } finally {

            // 获取响应数据，此时传输流已完成
            String responseBody = httpServletResponseWrapper.getResponseData("UTF-8");

            // 重新写数据到流中，传给客户端
            httpServletResponse.getOutputStream().write(responseBody.getBytes());

            log.info("response -------- path = {}, response = {}, status = {}, time = {}", path, responseBody, status, System.currentTimeMillis() - startTime);

        }


    }

    /**
     * 是否执行过滤器
     * @param request
     * @return
     */
    private boolean checkFilter(HttpServletRequest request) {


        String url = request.getRequestURI();

        //静态资源放行
        if(url.endsWith(".js")||url.endsWith(".css")||url.endsWith(".jpg") ||url.endsWith(".gif")||url.endsWith(".png")){
            return false;
        }

        //swagger放行
        if (url.endsWith("swagger-ui.html")) {
            return false;
        }
        String referer = request.getHeader("referer");
        if (StringUtils.isNotBlank(referer) && referer.contains("swagger")) {
            return false;
        }

        return  true;


    }

    /**
     * 获取请求IP
     * @param request
     * @return
     */
    private String getIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();

    }

    /**
     * 获取请求头
     * @param request
     * @return
     */
    private String getHttpHeader(HttpServletRequest request) {

        StringBuilder stringBuilder = new StringBuilder();

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            stringBuilder.append(headerName).append(": ").append(headerValue).append("\n");
        }

        return stringBuilder.toString();

    }

    /**
     * 获取请求参数
     * @param request
     * @return
     */
    private Map<String, String> getRequestParam(HttpServletRequest request) {

        Map<String, String> paramMap = new HashMap<>();

        // GET方式 或application/x-www-form-urlencoded
        NativeWebRequest webRequest = new ServletWebRequest(request);
        Map<String, String> pathParamMap = (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);
        if (pathParamMap != null) {
            paramMap.putAll(pathParamMap);
        }

        // POST/PATCH等方式,application/json
        String requestBody = null;
        try {
            requestBody = IOUtils.toString(request.getInputStream(), Charsets.UTF_8);
        } catch (IOException e) {
            logger.error("get request body error", e);
        }
        if (StringUtils.isNotBlank(requestBody)) {
            Map<String, String> bodyParamMap = JSONObject.parseObject(requestBody, new TypeReference<LinkedHashMap<String, String>>(){}, Feature.OrderedField);
            if (bodyParamMap != null) {
                paramMap.putAll(bodyParamMap);
            }
        }

        return paramMap;

    }

}




