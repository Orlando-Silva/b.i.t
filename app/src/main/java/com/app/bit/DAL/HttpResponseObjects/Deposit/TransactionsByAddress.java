
package com.app.bit.DAL.HttpResponseObjects.Deposit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionsByAddress {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("total_received")
    @Expose
    private Integer totalReceived;
    @SerializedName("total_sent")
    @Expose
    private Integer totalSent;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("unconfirmed_balance")
    @Expose
    private Integer unconfirmedBalance;
    @SerializedName("final_balance")
    @Expose
    private Integer finalBalance;
    @SerializedName("n_tx")
    @Expose
    private Integer nTx;
    @SerializedName("unconfirmed_n_tx")
    @Expose
    private Integer unconfirmedNTx;
    @SerializedName("final_n_tx")
    @Expose
    private Integer finalNTx;
    @SerializedName("txrefs")
    @Expose
    private List<Txref> txrefs = null;
    @SerializedName("unconfirmed_txrefs")
    @Expose
    private List<UnconfirmedTxref> unconfirmedTxrefs = null;
    @SerializedName("tx_url")
    @Expose
    private String txUrl;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getUnconfirmedBalance() {
        return unconfirmedBalance;
    }

    public void setUnconfirmedBalance(Integer unconfirmedBalance) {
        this.unconfirmedBalance = unconfirmedBalance;
    }

    public Integer getFinalBalance() {
        return finalBalance;
    }

    public void setFinalBalance(Integer finalBalance) {
        this.finalBalance = finalBalance;
    }

    public Integer getNTx() {
        return nTx;
    }

    public void setNTx(Integer nTx) {
        this.nTx = nTx;
    }

    public Integer getUnconfirmedNTx() {
        return unconfirmedNTx;
    }

    public void setUnconfirmedNTx(Integer unconfirmedNTx) {
        this.unconfirmedNTx = unconfirmedNTx;
    }

    public Integer getFinalNTx() {
        return finalNTx;
    }

    public void setFinalNTx(Integer finalNTx) {
        this.finalNTx = finalNTx;
    }

    public List<Txref> getTxrefs() {
        return txrefs;
    }

    public void setTxrefs(List<Txref> txrefs) {
        this.txrefs = txrefs;
    }

    public List<UnconfirmedTxref> getUnconfirmedTxrefs() {
        return unconfirmedTxrefs;
    }

    public void setUnconfirmedTxrefs(List<UnconfirmedTxref> unconfirmedTxrefs) {
        this.unconfirmedTxrefs = unconfirmedTxrefs;
    }

    public String getTxUrl() {
        return txUrl;
    }

    public void setTxUrl(String txUrl) {
        this.txUrl = txUrl;
    }

}
