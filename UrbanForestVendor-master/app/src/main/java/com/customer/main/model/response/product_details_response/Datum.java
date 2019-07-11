
package com.customer.main.model.response.product_details_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("pname")
    @Expose
    private String pname;
    @SerializedName("pdescription")
    @Expose
    private String pdescription;
    @SerializedName("catid")
    @Expose
    private String catid;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("size")
    @Expose
    private String size;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dprice")
    @Expose
    private String dprice;
    @SerializedName("potprice")
    @Expose
    private String potprice;
    @SerializedName("psize")
    @Expose
    private String psize;
    @SerializedName("potcat")
    @Expose
    private String potcat;
    @SerializedName("vid")
    @Expose
    private String vid;
    @SerializedName("pimage")
    @Expose
    private String pimage;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getCatid() {
        return catid;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDprice() {
        return dprice;
    }

    public void setDprice(String dprice) {
        this.dprice = dprice;
    }

    public String getPotprice() {
        return potprice;
    }

    public void setPotprice(String potprice) {
        this.potprice = potprice;
    }

    public String getPsize() {
        return psize;
    }

    public void setPsize(String psize) {
        this.psize = psize;
    }

    public String getPotcat() {
        return potcat;
    }

    public void setPotcat(String potcat) {
        this.potcat = potcat;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

}
