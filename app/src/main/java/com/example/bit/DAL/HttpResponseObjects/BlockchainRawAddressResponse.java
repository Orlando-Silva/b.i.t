
package com.example.bit.DAL.HttpResponseObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BlockchainRawAddressResponse {

    @SerializedName("hash160")
    @Expose
    private String hash160;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("n_tx")
    @Expose
    private Integer nTx;
    @SerializedName("total_received")
    @Expose
    private Integer totalReceived;
    @SerializedName("total_sent")
    @Expose
    private Integer totalSent;
    @SerializedName("final_balance")
    @Expose
    private Integer finalBalance;
    @SerializedName("txs")
    @Expose
    private List<Tx> txs = null;

    public String getHash160() {
        return hash160;
    }

    public void setHash160(String hash160) {
        this.hash160 = hash160;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNTx() {
        return nTx;
    }

    public void setNTx(Integer nTx) {
        this.nTx = nTx;
    }

    public Integer getTotalReceived() {
        return totalReceived;
    }

    public void setTotalReceived(Integer totalReceived) {
        this.totalReceived = totalReceived;
    }

    public Integer getTotalSent() {
        return totalSent;
    }

    public void setTotalSent(Integer totalSent) {
        this.totalSent = totalSent;
    }

    public Integer getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Integer finalBalance) {
        this.finalBalance = finalBalance;
    }

    public List<Tx> getTxs() {
        return txs;
    }

    public void setTxs(List<Tx> txs) {
        this.txs = txs;
    }

}
