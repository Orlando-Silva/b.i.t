
package com.example.bit.DAL.HttpResponseObjects.WithdrawResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("prev_hash")
    @Expose
    private String prevHash;
    @SerializedName("output_value")
    @Expose
    private Integer outputValue;
    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;
    @SerializedName("script")
    @Expose
    private String script;
    @SerializedName("script_type")
    @Expose
    private String scriptType;

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public Integer getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Integer outputValue) {
        this.outputValue = outputValue;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

}
