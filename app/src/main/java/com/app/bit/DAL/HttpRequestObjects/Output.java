
package com.app.bit.DAL.HttpRequestObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;
    @SerializedName("value")
    @Expose
    private Integer value;

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
