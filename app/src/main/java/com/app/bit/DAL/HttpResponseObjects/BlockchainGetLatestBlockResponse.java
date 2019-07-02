package com.app.bit.DAL.HttpResponseObjects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockchainGetLatestBlockResponse {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("time")
    @Expose
    private Long time;
    @SerializedName("block_index")
    @Expose
    private Long blockIndex;
    @SerializedName("height")
    @Expose
    private Long height;
    @SerializedName("txIndexes")
    @Expose
    private List<Long> txIndexes = null;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Long getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Long blockIndex) {
        this.blockIndex = blockIndex;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public List<Long> getTxIndexes() {
        return txIndexes;
    }

    public void setTxIndexes(List<Long> txIndexes) {
        this.txIndexes = txIndexes;
    }

}