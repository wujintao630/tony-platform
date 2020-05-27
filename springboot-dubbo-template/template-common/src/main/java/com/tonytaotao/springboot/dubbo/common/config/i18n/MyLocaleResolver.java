package com.tonytaotao.springboot.dubbo.common.config.i18n;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class MyLocaleResolver implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {

        //获取系统的默认区域信息
        Locale locale=Locale.getDefault();

        //获取自定义请求头信息
        String lang = httpServletRequest.getHeader(ValidationConfig.I18N_LANGUAGE);
        if (StringUtils.isEmpty(lang)){
            //获取自定义url参数信息
            lang = httpServletRequest.getParameter(ValidationConfig.I18N_LANGUAGE);
        }

        if (!StringUtils.isEmpty(lang)){
            String[] split=lang.split("_");
            //接收的第一个参数为：语言代码，国家代码
            locale=new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
