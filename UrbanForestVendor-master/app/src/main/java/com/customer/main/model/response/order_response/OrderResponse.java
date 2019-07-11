
package com.customer.main.model.response.order_response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderResponse {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("totalOrders")
    @Expose
    private Integer totalOrders;
    @SerializedName("cashOrders")
    @Expose
    private Integer cashOrders;
    @SerializedName("cardOrders")
    @Expose
    private Integer cardOrders;
    @SerializedName("completedOrders")
    @Expose
    private Integer completedOrders;
    @SerializedName("status")
    @Expose
    private Integer status;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getCashOrders() {
        return cashOrders;
    }

    public void setCashOrders(Integer cashOrders) {
        this.cashOrders = cashOrders;
    }

    public Integer getCardOrders() {
        return cardOrders;
    }

    public void setCardOrders(Integer cardOrders) {
        this.cardOrders = cardOrders;
    }

    public Integer getCompletedOrders() {
        return completedOrders;
    }

    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
