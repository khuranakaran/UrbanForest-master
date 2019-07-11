
package com.customer.main.model.response.OrderDetailsResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailsResponse {

    @SerializedName("order")
    @Expose
    private List<Order> order = null;
    @SerializedName("orderDetails")
    @Expose
    private List<OrderDetail> orderDetails = null;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("CustomerName")
    @Expose
    private String customerName;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
