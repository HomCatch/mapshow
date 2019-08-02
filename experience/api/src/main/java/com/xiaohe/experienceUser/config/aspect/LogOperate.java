package com.xiaohe.experienceUser.config.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Documented
public @interface LogOperate {
    //操作内容
    String description() default "";
}
