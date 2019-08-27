package com.reign.core.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: RequestParamAnnotation
 * @Description: 参数注解
 * @Author: wuwx
 * @Date: 2019-08-27 14:25
 **/
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParamAnnotation {
    String value() default "";
}
