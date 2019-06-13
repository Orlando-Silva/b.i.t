
package com.example.bit.DAL.HttpResponseObjects.WithdrawResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendWithdrawFirstStepResponse {

    @SerializedName("tx")
    @Expose
    private Tx tx;
    @SerializedName("tosign")
    @Expose
    private List<String> tosign = null;

    public Tx getTx() {
        return tx;
    }

    public void setTx(Tx tx) {
        this.tx = tx;
    }

    public List<String> getTosign() {
        return tosign;
    }

    public void setTosign(List<String> tosign) {
        this.tosign = tosign;
    }

}
