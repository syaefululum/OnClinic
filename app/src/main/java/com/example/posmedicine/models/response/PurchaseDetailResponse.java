package com.example.posmedicine.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 17-Feb-17.
 */

public class PurchaseDetailResponse {
    @SerializedName("purchase_headerid")
    @Expose
    private String purchaseHeaderId;
    @SerializedName("medicineid")
    @Expose
    private String medicineId;
    @SerializedName("unitid")
    @Expose
    private String unitId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("id")
    @Expose
    private int id;

    public String getPurchaseHeaderId() {
        return purchaseHeaderId;
    }

    public void setPurchaseHeaderId(String purchaseHeaderId) {
        this.purchaseHeaderId = purchaseHeaderId;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
