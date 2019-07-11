
package com.customer.main.model.request.get_my_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMyOrders {

    @SerializedName("customerid")
    @Expose
    private String customerid;

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

}
