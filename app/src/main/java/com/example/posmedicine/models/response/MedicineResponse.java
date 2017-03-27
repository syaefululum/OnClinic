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
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<Medicine> data = null;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<Medicine> getMedicine() {
        return data;
    }

    public void setMedicine(List<Medicine> data) {
        this.data = data;
    }
}
