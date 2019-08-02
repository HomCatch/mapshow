package com.xiaohe.mapshow.config.annotation;

import java.lang.annotation.*;

/**
 * demo用户拦截
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Documented
public @interface DemoIntercept {

}
