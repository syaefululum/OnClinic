package com.example.posmedicine.models.response;

import java.util.List;

import com.example.posmedicine.models.CashierHeaderTransaction;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 20-Feb-17.
 */

public class CashierHeaderResponse {
    @SerializedName("purchaseHeader")
    @Expose
    private List<CashierHeaderTransaction> data = null;

    public List<CashierHeaderTransaction> getHeaderTransaction() {
        return data;
    }

    public void setHeaderTransaction(List<CashierHeaderTransaction> data) {
        this.data = data;
    }

}
