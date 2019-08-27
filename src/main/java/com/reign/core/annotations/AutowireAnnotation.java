package com.reign.core.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: AutowireAnnotation
 * @Description: 依赖注入
 * @Author: wuwx
 * @Date: 2019-08-27 14:22
 **/
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AutowireAnnotation {
    String value() default "";
}
