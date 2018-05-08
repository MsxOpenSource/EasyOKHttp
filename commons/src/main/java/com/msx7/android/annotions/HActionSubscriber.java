package com.msx7.android.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 声明此注解的类，应该满足以下几个条件：<p>
 * 1、必须继承自rx.HActionSubscriber<p>
 * 2、必须包含如下构造函数<p>
 *     &emsp;&emsp;ClassName（{@link ActionData} actiondata,{@link IActionDataParse} parse)<p>
 * Created by xiaowei on 2017/7/18.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface HActionSubscriber {
}
