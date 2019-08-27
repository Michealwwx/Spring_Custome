package com.reign.core.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: RequestMappingAnnotation
 * @Description: 方法url映射
 * @Author: wuwx
 * @Date: 2019-08-27 14:23
 **/

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestMappingAnnotation {
    String value() default "";
}
