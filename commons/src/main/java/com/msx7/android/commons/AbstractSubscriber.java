package com.msx7.android.commons;

import com.msx7.android.annotions.ActionData;
import com.msx7.android.annotions.IActionDataParse;

import rx.Subscriber;

/**
 * Created by xiaowei on 2017/8/1.
 */

public abstract class AbstractSubscriber<T> extends Subscriber<T> {



    public AbstractSubscriber(ActionData<T> actionData, IActionDataParse<T> parse) {

    }


}