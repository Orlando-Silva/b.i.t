
package com.app.bit.DAL.HttpRequestObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendWithdrawFirstStepRequest {

    @SerializedName("inputs")
    @Expose
    private List<Input> inputs = null;
    @SerializedName("outputs")
    @Expose
    private List<Output> outputs = null;

    @SerializedName("preference")
    @Expose
    private String preference = "low";

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public List<Output> getOutputs() {
        return outputs;
    }

    public void setOutputs(List<Output> outputs) {
        this.outputs = outputs;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }
}
