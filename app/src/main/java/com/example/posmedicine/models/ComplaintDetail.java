package com.example.posmedicine.models;

/**
 * Created by Surya_N2267 on 3/2/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComplaintDetail {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("complaint_header_id")
    @Expose
    private String complaintHeaderId;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("complaintHeader")
    @Expose
    private ComplaintHeader complaintHeader;
    @SerializedName("service")
    @Expose
    private Service service;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComplaintHeaderId() {
        return complaintHeaderId;
    }

    public void setComplaintHeaderId(String complaintHeaderId) {
        this.complaintHeaderId = complaintHeaderId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ComplaintHeader getComplaintHeader() {
        return complaintHeader;
    }

    public void setComplaintHeader(ComplaintHeader complaintHeader) {
        this.complaintHeader = complaintHeader;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
