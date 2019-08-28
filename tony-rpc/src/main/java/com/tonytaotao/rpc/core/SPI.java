package com.tonytaotao.rpc.core;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SPI {

    /**
     * 扩展名，默认为 ""
     * @return
     */
    String value() default "";
}
