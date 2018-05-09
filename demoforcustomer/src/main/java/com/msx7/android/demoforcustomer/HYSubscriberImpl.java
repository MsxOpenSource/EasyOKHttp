package com.msx7.android.demoforcustomer;

import com.msx7.android.annotions.ActionData;
import com.msx7.android.annotions.HActionSubscriber;
import com.msx7.android.annotions.IActionDataParse;
import com.msx7.android.commons.AbstractSubscriber;

/**
 * 所 属 包：com.lovehome.android.aijiazhusou.common.flux
 * 文 件 名：HYSubscriberImpl
 * 描    述：
 * 作    者：xiaowei
 * 时    间：2017/6/2
 */
@HActionSubscriber
public class HYSubscriberImpl<T extends BaseBean> extends AbstractSubscriber<T> {
    ActionData<T>       actionData;
    IActionDataParse<T> parse;

    public HYSubscriberImpl(ActionData<T> actionData, IActionDataParse<T> parse) {
        super(actionData, parse);
        this.actionData = actionData;
        actionData.error = "请求异常,请稍后重新尝试!";
        this.parse = new Parse<>(parse);
        if (actionData == null) this.actionData = new ActionData<>();


    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        actionData.e = e;
        actionData.isSuccess = false;
        parse.parse(actionData); //可以将此处改为 用 eventbus 发生数据
    }

    @Override
    public void onNext(T t) {
        actionData.data = t;
        if (t.status != 200) {
            actionData.isSuccess = false;
            actionData.error = t.message;
        } else
            actionData.isSuccess = true;
        parse.parse(actionData);//可以将此处改为 用 eventbus 发生数据
    }


    class Parse<T> implements IActionDataParse<T> {
        IActionDataParse<T> parsed;

        public Parse(IActionDataParse<T> parsed) {
            this.parsed = parsed;
        }

        @Override
        public ActionData<T> parse(ActionData<T> data) {
            if (parsed != null) return parsed.parse(data);
            return data;
        }
    }
}
