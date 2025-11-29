package com.karza.aadhaarverify.model;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {
    
    @SerializedName("statusCode")
    private int statusCode;
    
    @SerializedName("requestId")
    private String requestId;
    
    @SerializedName("result")
    private Result result;
    
    @SerializedName("error")
    private String error;
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getRequestId() {
        return requestId;
    }
    
    public Result getResult() {
        return result;
    }
    
    public String getError() {
        return error;
    }
    
    public boolean isSuccess() {
        return statusCode == 101;
    }
    
    public static class Result {
        @SerializedName("message")
        private String message;
        
        @SerializedName("accessKey")
        private String accessKey;
        
        public String getMessage() {
            return message;
        }
        
        public String getAccessKey() {
            return accessKey;
        }
    }
}
