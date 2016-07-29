package puninar.view.ccms.Utility;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SerializedList implements Serializable {

    @SerializedName("id")
    public int id;

    @SerializedName("orders")
    public String orders;

    @SerializedName("pro_desc")
    public String pro_desc;

    @SerializedName("qty")
    public int qty;

   /* @SerializedName("mbl")
    public BigDecimal mbl;*/

    @SerializedName("checker_name")
    public String checker_name;

    @SerializedName("remarks")
    public String remarks;

    @SerializedName("startdate")
    public String startdate;

    @SerializedName("starttime")
    public String starttime;

    @SerializedName("image")
    public String image;

}

