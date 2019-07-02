
package com.app.bit.DAL.HttpResponseObjects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpendingOutpoint_ {

    @SerializedName("tx_index")
    @Expose
    private Integer txIndex;
    @SerializedName("n")
    @Expose
    private Integer n;

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

}
