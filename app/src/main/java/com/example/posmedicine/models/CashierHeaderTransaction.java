package com.example.posmedicine.models;

/**
 * Created by Syaeful_U1438 on 20-Feb-17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CashierHeaderTransaction {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("purchaseDetails")
    @Expose
    private List<CashierDetailTransaction> transactionDetails = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CashierDetailTransaction> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<CashierDetailTransaction> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

}