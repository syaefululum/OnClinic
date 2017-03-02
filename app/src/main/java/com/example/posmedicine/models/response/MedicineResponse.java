package com.example.posmedicine.models.response;

/**
 * Created by Syaeful_U1438 on 02-Feb-17.
 */

import java.util.List;

import com.example.posmedicine.models.Medicine;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private List<Medicine> medicine = null;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }

}
