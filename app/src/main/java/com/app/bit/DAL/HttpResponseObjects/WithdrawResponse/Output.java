
package com.app.bit.DAL.HttpResponseObjects.WithdrawResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Output {

    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("script")
    @Expose
    private String script;
    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;
    @SerializedName("script_type")
    @Expose
    private String scriptType;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

}
