package com.example.posmedicine.models;

/**
 * Created by Syaeful_U1438 on 02-Feb-17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicine implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("unitid")
    @Expose
    private int unitId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("date_stock")
    @Expose
    private String dateStock;
    @SerializedName("date_expiration")
    @Expose
    private String dateExpiration;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("unit")
    @Expose
    private Unit unit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateStock() {
        return dateStock;
    }

    public void setDateStock(String dateStock) {
        this.dateStock = dateStock;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitId=" + unitId +
                ", price='" + price + '\'' +
                ", type='" + type + '\'' +
                ", dateStock='" + dateStock + '\'' +
                ", dateExpiration='" + dateExpiration + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.quantity);
        dest.writeInt(this.unitId);
        dest.writeString(this.price);
        dest.writeString(this.type);
        dest.writeString(this.dateStock);
        dest.writeString(this.dateExpiration);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeParcelable(this.unit, flags);
    }

    public Medicine() {
    }

    protected Medicine(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.quantity = in.readString();
        this.unitId = in.readInt();
        this.price = in.readString();
        this.type = in.readString();
        this.dateStock = in.readString();
        this.dateExpiration = in.readString();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.unit = in.readParcelable(Unit.class.getClassLoader());
    }

    public static final Parcelable.Creator<Medicine> CREATOR = new Parcelable.Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel source) {
            return new Medicine(source);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };
}