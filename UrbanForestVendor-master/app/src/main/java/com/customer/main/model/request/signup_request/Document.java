
package com.customer.main.model.request.signup_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author neuron
 */
public class Document {

    @SerializedName("dname")
    @Expose
    private String dname;
    @SerializedName("ddata")
    @Expose
    private String ddata;

    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }

    public String getDdata() {
        return ddata;
    }

    public void setDdata(String ddata) {
        this.ddata = ddata;
    }

}
