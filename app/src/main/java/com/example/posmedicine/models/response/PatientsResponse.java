package com.example.posmedicine.models.response;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

import java.util.List;

import com.example.posmedicine.models.Patient;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientsResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Patient> patient = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Patient> getPatient() {
        return patient;
    }

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
