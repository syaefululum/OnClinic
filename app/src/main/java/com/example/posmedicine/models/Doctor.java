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
    @SerializedName("personid")
    @Expose
    private int personId;
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
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("person")
    @Expose
    private Person person;

    /**
     *
     * @param updatedAt
     * @param id
     * @param regNumber
     * @param status
     * @param createdAt
     * @param personId
     * @param resignDate
     * @param joinedDate
     */
    public Doctor(int id, int personId, int regNumber, String joinedDate, String resignDate, String status, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.personId = personId;
        this.regNumber = regNumber;
        this.joinedDate = joinedDate;
        this.resignDate = resignDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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


    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}

