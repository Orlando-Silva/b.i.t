
package com.example.bit.DAL.HttpResponseObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InputCypher {

    @SerializedName("prev_hash")
    @Expose
    private String prevHash;
    @SerializedName("output_index")
    @Expose
    private Long outputIndex;
    @SerializedName("script")
    @Expose
    private String script;
    @SerializedName("output_value")
    @Expose
    private Long outputValue;
    @SerializedName("sequence")
    @Expose
    private Long sequence;
    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;
    @SerializedName("script_type")
    @Expose
    private String scriptType;
    @SerializedName("age")
    @Expose
    private Long age;
    @SerializedName("witness")
    @Expose
    private List<String> witness = null;

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public Long getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(Long outputIndex) {
        this.outputIndex = outputIndex;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Long getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(Long outputValue) {
        this.outputValue = outputValue;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
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

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public List<String> getWitness() {
        return witness;
    }

    public void setWitness(List<String> witness) {
        this.witness = witness;
    }

}
