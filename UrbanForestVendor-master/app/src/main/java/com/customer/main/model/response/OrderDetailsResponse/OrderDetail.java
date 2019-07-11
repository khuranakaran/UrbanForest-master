
package com.customer.main.model.response.OrderDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("odid")
    @Expose
    private String odid;
    @SerializedName("oid")
    @Expose
    private String oid;
    @SerializedName("pid")
    @Expose
    private String pid;
    @SerializedName("oqty")
    @Expose
    private String oqty;
    @SerializedName("pprice")
    @Expose
    private String pprice;
    @SerializedName("pwpot")
    @Expose
    private String pwpot;
    @SerializedName("pname")
    @Expose
    private String pname;

    public String getOdid() {
        return odid;
    }

    public void setOdid(String odid) {
        this.odid = odid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getOqty() {
        return oqty;
    }

    public void setOqty(String oqty) {
        this.oqty = oqty;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPwpot() {
        return pwpot;
    }

    public void setPwpot(String pwpot) {
        this.pwpot = pwpot;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

}
