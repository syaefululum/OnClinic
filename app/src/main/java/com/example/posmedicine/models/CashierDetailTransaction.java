package com.example.posmedicine.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 20-Feb-17.
 */

public class CashierDetailTransaction implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("purchase_headerid")
    @Expose
    private String purchaseHeaderId;
    @SerializedName("medicineid")
    @Expose
    private String medicineId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unitid")
    @Expose
    private String unitId;
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
    @SerializedName("medicine")
    @Expose
    private Medicine medicine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.purchaseHeaderId);
        dest.writeString(this.medicineId);
        dest.writeString(this.quantity);
        dest.writeString(this.unitId);
        dest.writeString(this.price);
        dest.writeString(this.totalPrice);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeParcelable(this.medicine, flags);
    }

    public CashierDetailTransaction() {
    }

    protected CashierDetailTransaction(Parcel in) {
        this.id = in.readString();
        this.purchaseHeaderId = in.readString();
        this.medicineId = in.readString();
        this.quantity = in.readString();
        this.unitId = in.readString();
        this.price = in.readString();
        this.totalPrice = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.medicine = in.readParcelable(Medicine.class.getClassLoader());
    }

    public static final Parcelable.Creator<CashierDetailTransaction> CREATOR = new Parcelable.Creator<CashierDetailTransaction>() {
        @Override
        public CashierDetailTransaction createFromParcel(Parcel source) {
            return new CashierDetailTransaction(source);
        }

        @Override
        public CashierDetailTransaction[] newArray(int size) {
            return new CashierDetailTransaction[size];
        }
    };
}
