package com.example.posmedicine.models;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("reg_number")
    @Expose
    private int regNumber;
    @SerializedName("joined_date")
    @Expose
    private String joinedDate;
    @SerializedName("resign_date")
    @Expose
    private String resignDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("person_id")
    @Expose
    private int personId;
    @SerializedName("person_name")
    @Expose
    private String personName;
    @SerializedName("person_phone")
    @Expose
    private String personPhone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(int regNumber) {
        this.regNumber = regNumber;
    }

    public String getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(String joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getResignDate() {
        return resignDate;
    }

    public void setResignDate(String resignDate) {
        this.resignDate = resignDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonPhone() {
        return personPhone;
    }

    public void setPersonPhone(String personPhone) {
        this.personPhone = personPhone;
    }

    @Override
    public String toString() {
        return personName;
    }
}

