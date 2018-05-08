package com.msx7.android.annotions;

/**
 * 所 属 包：com.lovehome.android.aijiazhusou.common.flux
 * 文 件 名：IActionDataParse
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2017/6/2
 */
public interface IActionDataParse<T> {
    ActionData<T> parse(ActionData<T> data);
}
