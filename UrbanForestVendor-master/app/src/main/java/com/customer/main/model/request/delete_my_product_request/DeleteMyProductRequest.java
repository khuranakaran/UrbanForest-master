
package com.customer.main.model.request.delete_my_product_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteMyProductRequest {

    @SerializedName("productId")
    @Expose
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
