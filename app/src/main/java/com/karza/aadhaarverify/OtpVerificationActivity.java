package com.karza.aadhaarverify;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.karza.aadhaarverify.api.ApiClient;
import com.karza.aadhaarverify.model.AadhaarResponse;
import com.karza.aadhaarverify.model.VerifyRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerificationActivity extends AppCompatActivity {
    
    private EditText etOtp, etShareCode;
    private Button btnVerify, btnResend;
    private ProgressBar progressBar;
    private TextView tvTimer, tvAadhaarDisplay;
    
    private String aadhaarNumber;
    private String accessKey;
    private CountDownTimer countDownTimer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);
        
        // Get data from intent
        aadhaarNumber = getIntent().getStringExtra("aadhaar_number");
        accessKey = getIntent().getStringExtra("access_key");
        
        initViews();
        setupListeners();
        startTimer();
    }
    
    private void initViews() {
        etOtp = findViewById(R.id.etOtp);
        etShareCode = findViewById(R.id.etShareCode);
        btnVerify = findViewById(R.id.btnVerify);
        btnResend = findViewById(R.id.btnResend);
        progressBar = findViewById(R.id.progressBar);
        tvTimer = findViewById(R.id.tvTimer);
        tvAadhaarDisplay = findViewById(R.id.tvAadhaarDisplay);
        
        // Display masked Aadhaar number
        if (aadhaarNumber != null && aadhaarNumber.length() == 12) {
            String masked = "XXXX XXXX " + aadhaarNumber.substring(8);
            tvAadhaarDisplay.setText("Aadhaar: " + masked);
        }
    }
    
    private void setupListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateInputs();
            }
            
            @Override
            public void afterTextChanged(Editable s) {}
        };
        
        etOtp.addTextChangedListener(textWatcher);
        etShareCode.addTextChangedListener(textWatcher);
        
        btnVerify.setOnClickListener(v -> verifyOtp());
        btnResend.setOnClickListener(v -> {
            // Go back to main activity to resend OTP
            finish();
        });
    }
    
    private void validateInputs() {
        String otp = etOtp.getText().toString().trim();
        String shareCode = etShareCode.getText().toString().trim();
        btnVerify.setEnabled(otp.length() == 6 && shareCode.length() >= 4);
    }
    
    private void startTimer() {
        btnResend.setEnabled(false);
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Resend OTP in " + (millisUntilFinished / 1000) + "s");
            }
            
            @Override
            public void onFinish() {
                tvTimer.setText("");
                btnResend.setEnabled(true);
            }
        }.start();
    }
    
    private void verifyOtp() {
        String otp = etOtp.getText().toString().trim();
        String shareCode = etShareCode.getText().toString().trim();
        
        if (otp.length() != 6) {
            Toast.makeText(this, "Please enter 6-digit OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (shareCode.length() < 4) {
            Toast.makeText(this, "Share code must be at least 4 digits", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading(true);
        
        VerifyRequest request = new VerifyRequest(accessKey, otp, aadhaarNumber, shareCode);
        
        ApiClient.getApiService().verifyOtp(
            ApiClient.API_KEY,
            "application/json",
            request
        ).enqueue(new Callback<AadhaarResponse>() {
            @Override
            public void onResponse(Call<AadhaarResponse> call, Response<AadhaarResponse> response) {
                showLoading(false);
                
                if (response.isSuccessful() && response.body() != null) {
                    AadhaarResponse aadhaarResponse = response.body();
                    
                    if (aadhaarResponse.isSuccess() && aadhaarResponse.getResult() != null) {
                        // Navigate to details screen
                        Intent intent = new Intent(OtpVerificationActivity.this, 
                            AadhaarDetailsActivity.class);
                        intent.putExtra("aadhaar_data", serializeAadhaarData(aadhaarResponse));
                        intent.putExtra("aadhaar_number", aadhaarNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = aadhaarResponse.getError() != null ? 
                            aadhaarResponse.getError() : "Verification failed";
                        Toast.makeText(OtpVerificationActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(OtpVerificationActivity.this, 
                        "Verification failed. Please try again.", 
                        Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AadhaarResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(OtpVerificationActivity.this, 
                    "Network error: " + t.getMessage(), 
                    Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private String serializeAadhaarData(AadhaarResponse response) {
        // Serialize using Gson for passing to next activity
        return new com.google.gson.Gson().toJson(response);
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnVerify.setEnabled(!show);
        etOtp.setEnabled(!show);
        etShareCode.setEnabled(!show);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
