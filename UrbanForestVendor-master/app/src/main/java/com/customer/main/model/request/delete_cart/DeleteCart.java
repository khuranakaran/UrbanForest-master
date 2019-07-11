
package com.customer.main.model.request.delete_cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteCart {

    @SerializedName("cartid")
    @Expose
    private String cartid;

    public String getCartid() {
        return cartid;
    }

    public void setCartid(String cartid) {
        this.cartid = cartid;
    }

}
