package com.karza.aadhaarverify.api;

import com.karza.aadhaarverify.model.OtpRequest;
import com.karza.aadhaarverify.model.OtpResponse;
import com.karza.aadhaarverify.model.VerifyRequest;
import com.karza.aadhaarverify.model.AadhaarResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface KarzaApiService {
    
    // Send OTP to Aadhaar linked mobile
    @POST("v3/eaadhaar/otp")
    Call<OtpResponse> sendOtp(
        @Header("x-karza-key") String apiKey,
        @Header("Content-Type") String contentType,
        @Body OtpRequest request
    );
    
    // Verify OTP and get Aadhaar details
    @POST("v3/eaadhaar/file")
    Call<AadhaarResponse> verifyOtp(
        @Header("x-karza-key") String apiKey,
        @Header("Content-Type") String contentType,
        @Body VerifyRequest request
    );
}
