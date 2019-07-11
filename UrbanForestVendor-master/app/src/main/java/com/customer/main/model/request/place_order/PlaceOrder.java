
package com.customer.main.model.request.place_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrder {

    @SerializedName("customerid")
    @Expose
    private String customerid;
    @SerializedName("orderType")
    @Expose
    private String orderType;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderAddress")
    @Expose
    private String orderAddress;
    @SerializedName("orderMobile")
    @Expose
    private String orderMobile;
    @SerializedName("orderDetails")
    @Expose
    private List<OrderDetail> orderDetails = null;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderMobile() {
        return orderMobile;
    }

    public void setOrderMobile(String orderMobile) {
        this.orderMobile = orderMobile;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

}
