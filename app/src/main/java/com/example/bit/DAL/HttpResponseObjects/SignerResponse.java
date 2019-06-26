package com.example.bit.DAL.HttpResponseObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignerResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("headers")
    @Expose
    private Object headers;
    @SerializedName("multiValueHeaders")
    @Expose
    private Object multiValueHeaders;
    @SerializedName("body")
    @Expose
    private String body;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getHeaders() {
        return headers;
    }

    public void setHeaders(Object headers) {
        this.headers = headers;
    }

    public Object getMultiValueHeaders() {
        return multiValueHeaders;
    }

    public void setMultiValueHeaders(Object multiValueHeaders) {
        this.multiValueHeaders = multiValueHeaders;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
