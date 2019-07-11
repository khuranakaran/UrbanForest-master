
package com.customer.main.model.request.add_product_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddProductRequest {

    @SerializedName("productname")
    @Expose
    private String productname;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("subcategory")
    @Expose
    private String subcategory;
    @SerializedName("productsize")
    @Expose
    private String productsize;
    @SerializedName("productprice")
    @Expose
    private String productprice;
    @SerializedName("productdiscountprice")
    @Expose
    private String productdiscountprice;
    @SerializedName("potcategory")
    @Expose
    private String potcategory;
    @SerializedName("potsize")
    @Expose
    private String potsize;
    @SerializedName("potprice")
    @Expose
    private String potprice;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("image")
    @Expose
    private String image;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public String getProductsize() {
        return productsize;
    }

    public void setProductsize(String productsize) {
        this.productsize = productsize;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }

    public String getProductdiscountprice() {
        return productdiscountprice;
    }

    public void setProductdiscountprice(String productdiscountprice) {
        this.productdiscountprice = productdiscountprice;
    }

    public String getPotcategory() {
        return potcategory;
    }

    public void setPotcategory(String potcategory) {
        this.potcategory = potcategory;
    }

    public String getPotsize() {
        return potsize;
    }

    public void setPotsize(String potsize) {
        this.potsize = potsize;
    }

    public String getPotprice() {
        return potprice;
    }

    public void setPotprice(String potprice) {
        this.potprice = potprice;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "AddProductRequest{" +
                "productname='" + productname + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                ", productsize='" + productsize + '\'' +
                ", productprice='" + productprice + '\'' +
                ", productdiscountprice='" + productdiscountprice + '\'' +
                ", potcategory='" + potcategory + '\'' +
                ", potsize='" + potsize + '\'' +
                ", potprice='" + potprice + '\'' +
                ", vendor='" + vendor + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
