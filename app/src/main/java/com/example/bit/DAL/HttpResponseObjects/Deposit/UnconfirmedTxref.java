
package com.example.bit.DAL.HttpResponseObjects.Deposit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UnconfirmedTxref {

    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("tx_hash")
    @Expose
    private String txHash;
    @SerializedName("tx_input_n")
    @Expose
    private Integer txInputN;
    @SerializedName("tx_output_n")
    @Expose
    private Integer txOutputN;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("spent")
    @Expose
    private Boolean spent;
    @SerializedName("received")
    @Expose
    private String received;
    @SerializedName("confirmations")
    @Expose
    private Integer confirmations;
    @SerializedName("double_spend")
    @Expose
    private Boolean doubleSpend;
    @SerializedName("preference")
    @Expose
    private String preference;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
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

    public Boolean getSpent() {
        return spent;
    }

    public void setSpent(Boolean spent) {
        this.spent = spent;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public Integer getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    public Boolean getDoubleSpend() {
        return doubleSpend;
    }

    public void setDoubleSpend(Boolean doubleSpend) {
        this.doubleSpend = doubleSpend;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

}
