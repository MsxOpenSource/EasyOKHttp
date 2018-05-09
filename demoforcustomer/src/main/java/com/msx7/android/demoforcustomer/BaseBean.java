package com.msx7.android.demoforcustomer;

import com.google.gson.annotations.SerializedName;

public class BaseBean<T> {

    @SerializedName("status")
    public int status;

    @SerializedName("data")
    public T      data;
    @SerializedName("message")
    public String message;

}
