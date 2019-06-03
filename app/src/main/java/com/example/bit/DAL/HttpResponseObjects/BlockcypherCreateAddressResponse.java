package com.example.bit.DAL.HttpResponseObjects;

import com.google.gson.annotations.SerializedName;

public class BlockcypherCreateAddressResponse {

    @SerializedName("private")
    private String _private;

    @SerializedName("public")
    private String _public;

    @SerializedName("address")
    private String _address;

    @SerializedName("wif")
    private String _wif;

    public String get_private() {
        return _private;
    }

    public void set_private(String _private) {
        this._private = _private;
    }

    public String get_public() {
        return _public;
    }

    public void set_public(String _public) {
        this._public = _public;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_wif() {
        return _wif;
    }

    public void set_wif(String _wif) {
        this._wif = _wif;
    }
}
