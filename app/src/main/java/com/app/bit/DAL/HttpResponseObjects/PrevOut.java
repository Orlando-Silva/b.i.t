
package com.app.bit.DAL.HttpResponseObjects;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrevOut {

    @SerializedName("spent")
    @Expose
    private Boolean spent;
    @SerializedName("spending_outpoints")
    @Expose
    private List<SpendingOutpoint> spendingOutpoints = null;
    @SerializedName("tx_index")
    @Expose
    private Integer txIndex;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("addr")
    @Expose
    private String addr;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("n")
    @Expose
    private Integer n;
    @SerializedName("script")
    @Expose
    private String script;

    public Boolean getSpent() {
        return spent;
    }

    public void setSpent(Boolean spent) {
        this.spent = spent;
    }

    public List<SpendingOutpoint> getSpendingOutpoints() {
        return spendingOutpoints;
    }

    public void setSpendingOutpoints(List<SpendingOutpoint> spendingOutpoints) {
        this.spendingOutpoints = spendingOutpoints;
    }

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

}
