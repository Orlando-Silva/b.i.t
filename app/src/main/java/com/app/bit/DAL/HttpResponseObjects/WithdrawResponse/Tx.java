
package com.app.bit.DAL.HttpResponseObjects.WithdrawResponse;

import java.util.List;

import com.blockcypher.model.transaction.input.Input;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tx {

    @SerializedName("block_height")
    @Expose
    private Integer blockHeight;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("addresses")
    @Expose
    private List<String> addresses = null;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("fees")
    @Expose
    private Integer fees;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("preference")
    @Expose
    private String preference;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("inputs")
    @Expose
    private List<Input> inputs = null;
    @SerializedName("outputs")
    @Expose
    private List<Output> outputs = null;

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFees() {
        return fees;
    }

    public void setFees(Integer fees) {
        this.fees = fees;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

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

}
