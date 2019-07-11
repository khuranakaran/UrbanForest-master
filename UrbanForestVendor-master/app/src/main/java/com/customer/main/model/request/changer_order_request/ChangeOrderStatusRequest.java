
package com.customer.main.model.request.changer_order_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangeOrderStatusRequest {

    @SerializedName("orderid")
    @Expose
    private String orderid;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("vendorid")
    @Expose
    private String vendorid;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }
}
