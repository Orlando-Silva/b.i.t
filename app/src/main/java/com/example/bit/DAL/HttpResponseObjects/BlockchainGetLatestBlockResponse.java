package com.example.bit.DAL.HttpResponseObjects;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlockchainGetLatestBlockResponse {

    @SerializedName("hash")
    @Expose
    private String hash;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("block_index")
    @Expose
    private Integer blockIndex;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("txIndexes")
    @Expose
    private List<Integer> txIndexes = null;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getBlockIndex() {
        return blockIndex;
    }

    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public List<Integer> getTxIndexes() {
        return txIndexes;
    }

    public void setTxIndexes(List<Integer> txIndexes) {
        this.txIndexes = txIndexes;
    }

}