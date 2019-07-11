
package com.customer.main.model.request.get_product_details_by_id;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductIdRequest {

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
