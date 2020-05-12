package com.tonytaotao.springboot.dubbo.common.config;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Validator;

/**
 * 自定义hibernate-validator的message文件夹位置
 * 放在 i18n/ 目录下，文件名以message开头
 *
 * @author wujintao
 */
@Configuration
public class ValidationConfig {

    @Bean
    public Validator validator() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/message");

        LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
        validatorFactoryBean.setValidationMessageSource(messageSource);

        return validatorFactoryBean;
    }

}
