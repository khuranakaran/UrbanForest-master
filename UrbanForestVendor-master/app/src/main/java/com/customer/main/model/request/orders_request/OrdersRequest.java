
package com.customer.main.model.request.orders_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    OrdersRequest {

    @SerializedName("vendorid")
    @Expose
    private Integer vendorid;
    @SerializedName("orderid")
    @Expose
    private Integer orderid;

    public Integer getVendorid() {
        return vendorid;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

}
