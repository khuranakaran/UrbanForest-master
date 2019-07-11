
package com.customer.main.model.response.categories_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("catname")
    @Expose
    private String catname;
    @SerializedName("catimage")
    @Expose
    private String catimage;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public String getCatimage() {
        return catimage;
    }

    public void setCatimage(String catimage) {
        this.catimage = catimage;
    }

}
