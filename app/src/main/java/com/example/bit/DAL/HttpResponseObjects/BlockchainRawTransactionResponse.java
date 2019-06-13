
package com.example.bit.DAL.HttpResponseObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockchainRawTransactionResponse {

    @SerializedName("ver")
    @Expose
    private Integer ver;
    @SerializedName("inputs")
    @Expose
    private List<Input> inputs = null;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("block_height")
    @Expose
    private Integer blockHeight;
    @SerializedName("relayed_by")
    @Expose
    private String relayedBy;
    @SerializedName("out")
    @Expose
    private List<Out> out = null;
    @SerializedName("lock_time")
    @Expose
    private Integer lockTime;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("double_spend")
    @Expose
    private Boolean doubleSpend;
    @SerializedName("block_index")
    @Expose
    private Integer blockIndex;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("tx_index")
    @Expose
    private Integer txIndex;
    @SerializedName("vin_sz")
    @Expose
    private Integer vinSz;
    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("vout_sz")
    @Expose
    private Integer voutSz;

    public Integer getVer() {
        return ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public String getRelayedBy() {
        return relayedBy;
    }

    public void setRelayedBy(String relayedBy) {
        this.relayedBy = relayedBy;
    }

    public List<Out> getOut() {
        return out;
    }

    public void setOut(List<Out> out) {
        this.out = out;
    }

    public Integer getLockTime() {
        return lockTime;
    }

    public void setLockTime(Integer lockTime) {
        this.lockTime = lockTime;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

    public Integer getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Integer getVinSz() {
        return vinSz;
    }

    public void setVinSz(Integer vinSz) {
        this.vinSz = vinSz;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getVoutSz() {
        return voutSz;
    }

    public void setVoutSz(Integer voutSz) {
        this.voutSz = voutSz;
    }

}
