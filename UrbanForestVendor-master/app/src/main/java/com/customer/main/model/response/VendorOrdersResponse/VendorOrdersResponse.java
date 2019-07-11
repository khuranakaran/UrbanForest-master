
package com.customer.main.model.response.VendorOrdersResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorOrdersResponse {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
