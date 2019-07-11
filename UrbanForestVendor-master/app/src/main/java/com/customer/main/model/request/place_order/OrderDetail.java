
package com.customer.main.model.request.place_order;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("productid")
    @Expose
    private String productid;
    @SerializedName("productQty")
    @Expose
    private String productQty;
    @SerializedName("productPrice")
    @Expose
    private String productPrice;
    @SerializedName("productwithPot")
    @Expose
    private String productwithPot;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductwithPot() {
        return productwithPot;
    }

    public void setProductwithPot(String productwithPot) {
        this.productwithPot = productwithPot;
    }

}
