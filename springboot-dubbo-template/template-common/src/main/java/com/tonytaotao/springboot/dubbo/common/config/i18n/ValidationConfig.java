package com.tonytaotao.springboot.dubbo.common.config.i18n;

import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 自定义hibernate-validator的message文件夹位置
 * 放在 i18n/ 目录下，文件名以ValidationMessages开头
 *
 * @author tonytaotao
 */
@Configuration
public class ValidationConfig implements WebMvcConfigurer {

    public static final String I18N_LANGUAGE = "language";

    /**
     * 资源文件
     * @return
     */
    @Bean
    public ResourceBundleMessageSource resourceBundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //指定国际化的默认编码
        messageSource.setDefaultEncoding("UTF-8");
        //指定国际化的Resource Bundle地址
        messageSource.setBasename("ValidationMessages");

        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
        /*SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        //指定默认语言为中文
        localeResolver.setDefaultLocale(Locale.CHINA);
        return localeResolver;*/
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new MyLocaleChangeInterceptor();
        //自定义语言参数
        localeChangeInterceptor.setParamName(I18N_LANGUAGE);
        return localeChangeInterceptor;
    }

    @Bean
    public Validator validator() {

        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        //快速失败
        validatorFactoryBean.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, "true");
        //为Validator配置国际化
        validatorFactoryBean.setValidationMessageSource(resourceBundleMessageSource());

        return validatorFactoryBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}
