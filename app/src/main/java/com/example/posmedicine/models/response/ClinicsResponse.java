package com.example.posmedicine.models.response;

/**
 * Created by Surya_N2267 on 4/10/2017.
 */

import java.util.List;

import com.example.posmedicine.models.Clinic;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClinicsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Clinic> clinic = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Clinic> getClinic() {
        return clinic;
    }

    public void setClinic(List<Clinic> clinic) {
        this.clinic = clinic;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
