package com.example.posmedicine.models.response;

/**
 * Created by Surya_N2267 on 3/17/2017.
 */

import java.util.List;

import com.example.posmedicine.models.Service;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServicesResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private List<Service> service = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Service> getService() {
        return service;
    }

    public void setService(List<Service> service) {
        this.service = service;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
