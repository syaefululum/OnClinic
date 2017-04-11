package com.example.posmedicine.models.response;
import java.util.List;

import com.example.posmedicine.models.PrescriptionHeader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Syaeful_U1438 on 10-Apr-17.
 */

public class PrescriptionHeaderResponse {
    @SerializedName("status")
    @Expose
    private boolean status;
    @SerializedName("data")
    @Expose
    private List<PrescriptionHeader> prescriptionHeaders = null;
    @SerializedName("message")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<PrescriptionHeader> getPrescriptionHeaders() {
        return prescriptionHeaders;
    }

    public void setPrescriptionHeaders(List<PrescriptionHeader> prescriptionHeaders) {
        this.prescriptionHeaders = prescriptionHeaders;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
