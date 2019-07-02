
package com.app.bit.DAL.HttpResponseObjects.WithdrawResponse;

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

    @SerializedName("signatures")
    @Expose
    private List<String> signatures = null;
    @SerializedName("pubkeys")
    @Expose
    private List<String> pubkeys = null;

    @SerializedName("errors")
    @Expose
    private List<Error> errors = null;

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

    public List<String> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public List<String> getPubkeys() {
        return pubkeys;
    }

    public void setPubkeys(List<String> pubkeys) {
        this.pubkeys = pubkeys;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }
}
