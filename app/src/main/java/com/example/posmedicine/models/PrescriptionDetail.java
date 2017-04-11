package com.example.posmedicine.models;

/**
 * Created by Syaeful_U1438 on 10-Apr-17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PrescriptionDetail {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("medicine_name")
    @Expose
    private String medicineName;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unit_id")
    @Expose
    private int unitId;
    @SerializedName("unit_name")
    @Expose
    private String unitName;
    @SerializedName("note")
    @Expose
    private String note;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
