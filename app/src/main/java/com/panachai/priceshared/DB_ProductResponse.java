package com.panachai.priceshared;

/**
 * Created by KILLERCON on 16/4/2560.
 */

public class DB_ProductResponse {


    /**
     * proID : 00000000001
     * proName : IPHONE7
     * catDeID : 00000000001
     * proDes : ไอโฟน7 ขนาดความจุ 64GB รองรับ wifi ac
     * proDisplay :
     */

    private int proID;
    private String proName;
    private int catDeID;
    private String proDes;
    private String proDisplay;

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getCatDeID() {
        return catDeID;
    }

    public void setCatDeID(int catDeID) {
        this.catDeID = catDeID;
    }

    public String getProDes() {
        return proDes;
    }

    public void setProDes(String proDes) {
        this.proDes = proDes;
    }

    public String getProDisplay() {
        return proDisplay;
    }

    public void setProDisplay(String proDisplay) {
        this.proDisplay = proDisplay;
    }
}
