package com.msx7.android.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by xiaowei on 2017/7/26.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface HController {
    Class Interface();

    String packageName() default "";
}
