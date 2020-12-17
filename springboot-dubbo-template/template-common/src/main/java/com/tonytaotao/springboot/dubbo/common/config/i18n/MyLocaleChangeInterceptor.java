package com.tonytaotao.springboot.dubbo.common.config.i18n;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyLocaleChangeInterceptor extends LocaleChangeInterceptor {

    /**
     * 重写获取国际化标志的，可以从请求头或者url中获取，优先级 请求头 > url
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws ServletException
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String newLocale = request.getHeader(this.getParamName());

        if (newLocale == null) {
            newLocale = request.getParameter(this.getParamName());
        }

        if (newLocale != null && this.checkHttpMethod(request.getMethod())) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            try {
                localeResolver.setLocale(request, response, this.parseLocaleValue(newLocale));
            } catch (IllegalArgumentException var7) {
                if (!this.isIgnoreInvalidLocale()) {
                    throw var7;
                }

                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + var7.getMessage());
                }
            }
        }

        return true;
    }

    /**
     * 复制LocaleChangeInterceptor 中的 checkHttpMethod 私有方法
     * @param currentMethod
     * @return
     */
    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = this.getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        } else {
            String[] var3 = configuredMethods;
            int var4 = configuredMethods.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String configuredMethod = var3[var5];
                if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                    return true;
                }
            }

            return false;
        }
    }
}
