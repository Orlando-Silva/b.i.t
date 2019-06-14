
package com.example.bit.DAL.HttpResponseObjects.Deposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Txref {

    @SerializedName("tx_hash")
    @Expose
    private String txHash;
    @SerializedName("block_height")
    @Expose
    private Integer blockHeight;
    @SerializedName("tx_input_n")
    @Expose
    private Integer txInputN;
    @SerializedName("tx_output_n")
    @Expose
    private Integer txOutputN;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("ref_balance")
    @Expose
    private Integer refBalance;
    @SerializedName("spent")
    @Expose
    private Boolean spent;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("double_spend")
    @Expose
    private Boolean doubleSpend;

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public Integer getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    public Integer getTxInputN() {
        return txInputN;
    }

    public void setTxInputN(Integer txInputN) {
        this.txInputN = txInputN;
    }

    public Integer getTxOutputN() {
        return txOutputN;
    }

    public void setTxOutputN(Integer txOutputN) {
        this.txOutputN = txOutputN;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getRefBalance() {
        return refBalance;
    }

    public void setRefBalance(Integer refBalance) {
        this.refBalance = refBalance;
    }

    public Boolean getSpent() {
        return spent;
    }

    public void setSpent(Boolean spent) {
        this.spent = spent;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

}
