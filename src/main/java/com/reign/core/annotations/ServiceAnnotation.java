package com.reign.core.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: ServiceAnnotation
 * @Description: Service注解
 * @Author: wuwx
 * @Date: 2019-08-27 13:33
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceAnnotation {
    String value() default "";
}
