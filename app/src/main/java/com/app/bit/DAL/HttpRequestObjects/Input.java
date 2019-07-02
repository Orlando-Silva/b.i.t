
package com.app.bit.DAL.HttpRequestObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;

    public List<String> getAddresses() {
        return addresses;
    }

    @SerializedName("script_type")
    @Expose
    private String script_type;

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getScript_type() {
        return script_type;
    }

    public void setScript_type(String script_type) {
        this.script_type = script_type;
    }
}
