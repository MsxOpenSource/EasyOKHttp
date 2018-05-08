package com.msx7.android.annotions;

/**
 * 所 属 包：com.lovehome.android.aijiazhusou.common.flux
 * 文 件 名：ActionData
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2017/6/2
 */
public class ActionData<T> {
    public T data;

    public Object[] postData;

    public boolean isSuccess;

    public String error;

    public Throwable e;

    public ActionData() {
    }


    public ActionData(Object[] postData) {
        this.postData = postData;
    }
}
