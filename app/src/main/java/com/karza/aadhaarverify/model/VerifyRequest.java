package com.karza.aadhaarverify.model;

import com.google.gson.annotations.SerializedName;

public class VerifyRequest {
    
    @SerializedName("accessKey")
    private String accessKey;
    
    @SerializedName("otp")
    private String otp;
    
    @SerializedName("aadhaarNo")
    private String aadhaarNo;
    
    @SerializedName("consent")
    private String consent;
    
    @SerializedName("shareCode")
    private String shareCode;
    
    public VerifyRequest(String accessKey, String otp, String aadhaarNo, String shareCode) {
        this.accessKey = accessKey;
        this.otp = otp;
        this.aadhaarNo = aadhaarNo;
        this.consent = "Y";
        this.shareCode = shareCode;
    }
    
    public String getAccessKey() {
        return accessKey;
    }
    
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }
    
    public String getOtp() {
        return otp;
    }
    
    public void setOtp(String otp) {
        this.otp = otp;
    }
    
    public String getAadhaarNo() {
        return aadhaarNo;
    }
    
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
    
    public String getShareCode() {
        return shareCode;
    }
    
    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }
}
