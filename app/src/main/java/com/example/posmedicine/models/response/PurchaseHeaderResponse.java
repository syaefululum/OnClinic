package com.example.posmedicine.models.response;

import com.example.posmedicine.models.CashierDetailTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 17-Feb-17.
 */

public class PurchaseHeaderResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private CashierDetailTransaction data;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public CashierDetailTransaction getData() {
        return data;
    }

    public void setData(CashierDetailTransaction data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
