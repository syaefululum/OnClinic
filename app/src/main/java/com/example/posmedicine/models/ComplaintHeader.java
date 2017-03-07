package com.example.posmedicine.models;

/**
 * Created by Surya_N2267 on 3/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintHeader {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("patient_id")
    @Expose
    private String patientId;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("registered_date")
    @Expose
    private String registeredDate;
    @SerializedName("checkup_date")
    @Expose
    private String checkupDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("doctor")
    @Expose
    private Doctor doctor;
    @SerializedName("patient")
    @Expose
    private Patient patient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(String registeredDate) {
        this.registeredDate = registeredDate;
    }

    public String getCheckupDate() {
        return checkupDate;
    }

    public void setCheckupDate(String checkupDate) {
        this.checkupDate = checkupDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

}
