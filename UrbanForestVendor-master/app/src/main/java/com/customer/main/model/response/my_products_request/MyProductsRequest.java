
package com.customer.main.model.response.my_products_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyProductsRequest {

    @SerializedName("vendorid")
    @Expose
    private String vendorid;

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

}
