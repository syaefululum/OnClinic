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
    private int id;
    @SerializedName("purchase_header_id")
    @Expose
    private int purchaseHeaderId;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_price")
    @Expose
    private String totalPrice;
    @SerializedName("medicine_id")
    @Expose
    private int medicineId;
    @SerializedName("unit_id")
    @Expose
    private int unitId;
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("unit_name")
    @Expose
    private String unitName;

    protected CashierDetailTransaction(Parcel in) {
        id = in.readInt();
        purchaseHeaderId = in.readInt();
        quantity = in.readString();
        price = in.readString();
        totalPrice = in.readString();
        medicineId = in.readInt();
        unitId = in.readInt();
        medicineName = in.readString();
        unitName = in.readString();
    }

    public static final Creator<CashierDetailTransaction> CREATOR = new Creator<CashierDetailTransaction>() {
        @Override
        public CashierDetailTransaction createFromParcel(Parcel in) {
            return new CashierDetailTransaction(in);
        }

        @Override
        public CashierDetailTransaction[] newArray(int size) {
            return new CashierDetailTransaction[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPurchaseHeaderId() {
        return purchaseHeaderId;
    }

    public void setPurchaseHeaderId(int purchaseHeaderId) {
        this.purchaseHeaderId = purchaseHeaderId;
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

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(purchaseHeaderId);
        dest.writeString(quantity);
        dest.writeString(price);
        dest.writeString(totalPrice);
        dest.writeInt(medicineId);
        dest.writeInt(unitId);
        dest.writeString(medicineName);
        dest.writeString(unitName);
    }
}
