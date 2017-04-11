package com.example.posmedicine.models.response;

import com.example.posmedicine.models.PrescriptionHeader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 11-Apr-17.
 */

public class PrescriptionHeaderSingleResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private PrescriptionHeader prescriptionHeaders;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public PrescriptionHeader getPrescriptionHeaders() {
        return prescriptionHeaders;
    }

    public void setPrescriptionHeaders(PrescriptionHeader prescriptionHeaders) {
        this.prescriptionHeaders = prescriptionHeaders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
