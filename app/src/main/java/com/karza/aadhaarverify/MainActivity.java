package com.karza.aadhaarverify;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karza.aadhaarverify.api.ApiClient;
import com.karza.aadhaarverify.model.OtpRequest;
import com.karza.aadhaarverify.model.OtpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    
    private EditText etAadhaar;
    private Button btnSendOtp;
    private ProgressBar progressBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        etAadhaar = findViewById(R.id.etAadhaar);
        btnSendOtp = findViewById(R.id.btnSendOtp);
        progressBar = findViewById(R.id.progressBar);
    }
    
    private void setupListeners() {
        etAadhaar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnSendOtp.setEnabled(s.length() == 12);
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                // Format Aadhaar number with spaces
                String text = s.toString().replaceAll(" ", "");
                if (text.length() > 12) {
                    text = text.substring(0, 12);
                }
            }
        });
        
        btnSendOtp.setOnClickListener(v -> sendOtp());
    }
    
    private void sendOtp() {
        String aadhaarNumber = etAadhaar.getText().toString().trim().replaceAll(" ", "");
        
        if (aadhaarNumber.length() != 12) {
            Toast.makeText(this, "Please enter valid 12-digit Aadhaar number", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate Aadhaar number (basic validation)
        if (!aadhaarNumber.matches("\\d{12}")) {
            Toast.makeText(this, "Aadhaar number should contain only digits", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading(true);
        
        OtpRequest request = new OtpRequest(aadhaarNumber);
        
        ApiClient.getApiService().sendOtp(
            ApiClient.API_KEY,
            "application/json",
            request
        ).enqueue(new Callback<OtpResponse>() {
            @Override
            public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                showLoading(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    OtpResponse otpResponse = response.body();
                    
                    if (otpResponse.isSuccess()) {
                        // OTP sent successfully, navigate to OTP verification screen
                        String accessKey = otpResponse.getResult() != null ? 
                            otpResponse.getResult().getAccessKey() : "";
                        
                        Intent intent = new Intent(MainActivity.this, OtpVerificationActivity.class);
                        intent.putExtra("aadhaar_number", aadhaarNumber);
                        intent.putExtra("access_key", accessKey);
                        startActivity(intent);
                        
                        Toast.makeText(MainActivity.this, 
                            "OTP sent to registered mobile number", 
                            Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = otpResponse.getError() != null ? 
                            otpResponse.getError() : "Failed to send OTP";
                        Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, 
                        "Failed to send OTP. Please try again.", 
                        Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<OtpResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(MainActivity.this, 
                    "Network error: " + t.getMessage(), 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnSendOtp.setEnabled(!show);
        etAadhaar.setEnabled(!show);
    }
}
