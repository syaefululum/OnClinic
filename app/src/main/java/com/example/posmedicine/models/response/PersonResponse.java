package com.example.posmedicine.models.response;

/**
 * Created by Surya_N2267 on 2/6/2017.
 */

import com.example.posmedicine.models.Person;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("data")
    @Expose
    private Person person;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

}
