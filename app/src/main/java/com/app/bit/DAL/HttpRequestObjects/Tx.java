
package com.app.bit.DAL.HttpRequestObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tx {

    @SerializedName("block_hash")
    @Expose
    private String blockHash;
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
    @SerializedName("relayed_by")
    @Expose
    private String relayedBy;
    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("received")
    @Expose
    private String received;
    @SerializedName("ver")
    @Expose
    private Integer ver;
    @SerializedName("lock_time")
    @Expose
    private Integer lockTime;
    @SerializedName("double_spend")
    @Expose
    private Boolean doubleSpend;
    @SerializedName("vin_sz")
    @Expose
    private Integer vinSz;
    @SerializedName("vout_sz")
    @Expose
    private Integer voutSz;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("inputs")
    @Expose
    private List<Input> inputs = null;
    @SerializedName("outputs")
    @Expose
    private List<Output> outputs = null;

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

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

    public String getRelayedBy() {
        return relayedBy;
    }

    public void setRelayedBy(String relayedBy) {
        this.relayedBy = relayedBy;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public Integer getLockTime() {
        return lockTime;
    }

    public void setLockTime(Integer lockTime) {
        this.lockTime = lockTime;
    }

    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

    public Integer getVinSz() {
        return vinSz;
    }

    public void setVinSz(Integer vinSz) {
        this.vinSz = vinSz;
    }

    public Integer getVoutSz() {
        return voutSz;
    }

    public void setVoutSz(Integer voutSz) {
        this.voutSz = voutSz;
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
