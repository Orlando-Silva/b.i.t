package com.example.bit.DAL.HttpRequestObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignerRequest {

    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("key")
    @Expose
    private String key;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
