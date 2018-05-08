package com.msx7.android.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 声明此注解的类，必须要包含一个静态的retrofit()方法，返回 {@link retrofit2.Retrofit}对象
 * <p>
 * Created by xiaowei on 2017/7/18.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface HRetrofit {
}
