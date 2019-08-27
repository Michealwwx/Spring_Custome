package com.reign.core.annotations;

import java.lang.annotation.*;

/**
 * @ClassName: ControllerAnnotation
 * @Description: Controller注解
 * @Author: wuwx
 * @Date: 2019-08-27 13:32
 **/

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerAnnotation {
    String value() default "";
}
