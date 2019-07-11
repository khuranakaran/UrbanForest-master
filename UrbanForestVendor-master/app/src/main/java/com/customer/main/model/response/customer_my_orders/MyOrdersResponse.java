
package com.customer.main.model.response.customer_my_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MyOrdersResponse {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("vendorName")
    @Expose
    private Object vendorName;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Object getVendorName() {
        return vendorName;
    }

    public void setVendorName(Object vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
