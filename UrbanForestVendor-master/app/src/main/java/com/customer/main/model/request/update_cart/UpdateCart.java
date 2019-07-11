
package com.customer.main.model.request.update_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateCart {

    @SerializedName("cartid")
    @Expose
    private String cartid;
    @SerializedName("customerid")
    @Expose
    private String customerid;
    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("productqty")
    @Expose
    private String productqty;

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductqty() {
        return productqty;
    }

    public void setProductqty(String productqty) {
        this.productqty = productqty;
    }

}
