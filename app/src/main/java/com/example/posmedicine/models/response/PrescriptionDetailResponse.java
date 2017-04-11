package com.example.posmedicine.models.response;
import com.example.posmedicine.models.PrescriptionDetail;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 10-Apr-17.
 */

public class PrescriptionDetailResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private PrescriptionDetail prescriptionDetail;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PrescriptionDetail getPrescriptionDetail() {
        return prescriptionDetail;
    }

    public void setData(PrescriptionDetail prescriptionDetail) {
        this.prescriptionDetail = prescriptionDetail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
