package com.example.posmedicine.models.response;

import com.example.posmedicine.models.CashierHeaderTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Syaeful_U1438 on 24-Mar-17.
 */

public class CashierHeaderListResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("total_query")
    @Expose
    private int totalQuery;
    @SerializedName("data")
    @Expose
    private List<CashierHeaderTransaction> data = null;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTotalQuery() {
        return totalQuery;
    }

    public void setTotalQuery(int totalQuery) {
        this.totalQuery = totalQuery;
    }

    public List<CashierHeaderTransaction> getData() {
        return data;
    }

    public void setData(List<CashierHeaderTransaction> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
