package com.panachai.priceshared;

/**
 * Created by KILLERCON on 18/4/2560.
 */

public class DB_ProductdetailResponse {


    /**
     * proDeID : 00000000001
     * proID : 00000000001
     * proDePrice : 27000
     * proDeDes : ลดราคาจาก 32000 เหลือ 27000
     * supDeID : 00000000001
     * cusID : 00000000010
     * proDeScore : 5
     * proDeDate : 2017-04-15
     * proDeStatus : 0
     */

    private int proDeID;
    private int proID;
    private double proDePrice;
    private String proDeDes;
    private int supDeID;
    private int cusID;
    private float proDeScore;
    private String proDeDate;
    private String proDeStatus;

    public int getProDeID() {
        return proDeID;
    }

    public void setProDeID(int proDeID) {
        this.proDeID = proDeID;
    }

    public int getProID() {
        return proID;
    }

    public void setProID(int proID) {
        this.proID = proID;
    }

    public double getProDePrice() {
        return proDePrice;
    }

    public void setProDePrice(double proDePrice) {
        this.proDePrice = proDePrice;
    }

    public String getProDeDes() {
        return proDeDes;
    }

    public void setProDeDes(String proDeDes) {
        this.proDeDes = proDeDes;
    }

    public int getSupDeID() {
        return supDeID;
    }

    public void setSupDeID(int supDeID) {
        this.supDeID = supDeID;
    }

    public int getCusID() {
        return cusID;
    }

    public void setCusID(int cusID) {
        this.cusID = cusID;
    }

    public float getProDeScore() {
        return proDeScore;
    }

    public void setProDeScore(float proDeScore) {
        this.proDeScore = proDeScore;
    }

    public String getProDeDate() {
        return proDeDate;
    }

    public void setProDeDate(String proDeDate) {
        this.proDeDate = proDeDate;
    }

    public String getProDeStatus() {
        return proDeStatus;
    }

    public void setProDeStatus(String proDeStatus) {
        this.proDeStatus = proDeStatus;
    }
}
