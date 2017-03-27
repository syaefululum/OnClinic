package com.example.posmedicine.models;

/**
 * Created by Surya_N2267 on 3/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintHeader {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("patient_id")
    @Expose
    private Integer patientId;
    @SerializedName("registered_date")
    @Expose
    private String registeredDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("patient_name")
    @Expose
    private String patientName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

}
