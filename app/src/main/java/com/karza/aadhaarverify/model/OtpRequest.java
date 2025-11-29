package com.karza.aadhaarverify.model;

import com.google.gson.annotations.SerializedName;

public class OtpRequest {
    
    @SerializedName("aadhaarNo")
    private String aadhaarNo;
    
    @SerializedName("consent")
    private String consent;
    
    @SerializedName("clientData")
    private ClientData clientData;
    
    public OtpRequest(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
        this.consent = "Y";
        this.clientData = new ClientData();
    }
    
    public String getAadhaarNo() {
        return aadhaarNo;
    }
    
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
    
    public String getConsent() {
        return consent;
    }
    
    public void setConsent(String consent) {
        this.consent = consent;
    }
    
    public static class ClientData {
        @SerializedName("caseId")
        private String caseId;
        
        public ClientData() {
            this.caseId = "case_" + System.currentTimeMillis();
        }
    }
}
