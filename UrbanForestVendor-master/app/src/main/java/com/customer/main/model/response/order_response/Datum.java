
package com.customer.main.model.response.order_response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("cid")
    @Expose
    private String cid;
    @SerializedName("otype")
    @Expose
    private String otype;
    @SerializedName("totalamount")
    @Expose
    private String totalamount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("oaddress")
    @Expose
    private String oaddress;
    @SerializedName("omobile")
    @Expose
    private String omobile;
    @SerializedName("vendorid")
    @Expose
    private String vendorid;
    @SerializedName("deliverd")
    @Expose
    private String deliverd;

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOaddress() {
        return oaddress;
    }

    public void setOaddress(String oaddress) {
        this.oaddress = oaddress;
    }

    public String getOmobile() {
        return omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getVendorid() {
        return vendorid;
    }

    public void setVendorid(String vendorid) {
        this.vendorid = vendorid;
    }

    public String getDeliverd() {
        return deliverd;
    }

    public void setDeliverd(String deliverd) {
        this.deliverd = deliverd;
    }

}
