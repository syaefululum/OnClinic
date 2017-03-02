package com.example.posmedicine.models.response;

import com.example.posmedicine.models.Unit;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 28-Feb-17.
 */

public class UnitSingleDataResponse {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private Unit unit;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setData(Unit unit) {
        this.unit = unit;
    }
}
