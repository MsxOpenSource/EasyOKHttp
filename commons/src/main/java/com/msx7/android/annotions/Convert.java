package com.msx7.android.annotions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 声明此注解的类， 必须包含一个如下的方法：
 * <code>
 * &emsp; public static final  &lt;T&gt; Observable&lt;T&gt; subscribeOn(Observable&lt;T&gt; observable) {
 * &emsp;&emsp; return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
 * &emsp; }
 * </code>
 * <p>
 * Created by xiaowei on 2017/7/18.
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface Convert {


}
