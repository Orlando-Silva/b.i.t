
package com.example.bit.DAL.HttpResponseObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Input {

    @SerializedName("sequence")
    @Expose
    private Long sequence;
    @SerializedName("witness")
    @Expose
    private String witness;
    @SerializedName("prev_out")
    @Expose
    private PrevOut prevOut;
    @SerializedName("script")
    @Expose
    private String script;

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public PrevOut getPrevOut() {
        return prevOut;
    }

    public void setPrevOut(PrevOut prevOut) {
        this.prevOut = prevOut;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

}
