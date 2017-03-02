package com.example.posmedicine.models;

/**
 * Created by Syaeful_U1438 on 10-Feb-17.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransactionMedicine {
    @SerializedName("medicineid")
    @Expose
    private int medicineId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("unitid")
    @Expose
    private int unitId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("total_price")
    @Expose
    private String 	totalPrice;

    private Medicine medicine;

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
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

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}