package com.karza.aadhaarverify;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.karza.aadhaarverify.model.AadhaarResponse;

public class AadhaarDetailsActivity extends AppCompatActivity {
    
    private ImageView ivPhoto, ivAadhaarFront, ivAadhaarBack;
    private TextView tvName, tvAddress, tvAadhaarNumber, tvDob, tvGender, tvMobile;
    private CardView cardPhoto, cardFront, cardBack;
    
    private AadhaarResponse aadhaarResponse;
    private String aadhaarNumber;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar_details);
        
        // Get data from intent
        String jsonData = getIntent().getStringExtra("aadhaar_data");
        aadhaarNumber = getIntent().getStringExtra("aadhaar_number");
        
        if (jsonData != null) {
            aadhaarResponse = new Gson().fromJson(jsonData, AadhaarResponse.class);
        }
        
        initViews();
        displayData();
    }
    
    private void initViews() {
        ivPhoto = findViewById(R.id.ivPhoto);
        ivAadhaarFront = findViewById(R.id.ivAadhaarFront);
        ivAadhaarBack = findViewById(R.id.ivAadhaarBack);
        
        tvName = findViewById(R.id.tvName);
        tvAddress = findViewById(R.id.tvAddress);
        tvAadhaarNumber = findViewById(R.id.tvAadhaarNumber);
        tvDob = findViewById(R.id.tvDob);
        tvGender = findViewById(R.id.tvGender);
        tvMobile = findViewById(R.id.tvMobile);
        
        cardPhoto = findViewById(R.id.cardPhoto);
        cardFront = findViewById(R.id.cardFront);
        cardBack = findViewById(R.id.cardBack);
    }
    
    private void displayData() {
        if (aadhaarResponse == null || aadhaarResponse.getResult() == null) {
            return;
        }
        
        AadhaarResponse.DataFromAadhaar data = aadhaarResponse.getResult().getDataFromAadhaar();
        
        if (data != null) {
            // Display Name
            String name = data.getName();
            tvName.setText(name != null ? name : "N/A");
            
            // Display Full Address
            String address = data.getFullAddress();
            tvAddress.setText(address != null && !address.isEmpty() ? address : "N/A");
            
            // Display Aadhaar Number (UNMASKED - Full number as requested)
            tvAadhaarNumber.setText(aadhaarNumber != null ? formatAadhaarNumber(aadhaarNumber) : "N/A");
            
            // Display DOB
            String dob = data.getDob();
            tvDob.setText(dob != null ? dob : "N/A");
            
            // Display Gender
            String gender = data.getGender();
            tvGender.setText(gender != null ? gender : "N/A");
            
            // Display Mobile Status
            String mobileHash = data.getMobileHash();
            if (mobileHash != null && !mobileHash.isEmpty()) {
                tvMobile.setText("Mobile Linked: Yes (Verified)");
            } else {
                tvMobile.setText("Mobile Linked: Status Unknown");
            }
            
            // Display Photo
            String photoBase64 = data.getPhoto();
            if (photoBase64 != null && !photoBase64.isEmpty()) {
                try {
                    Bitmap photoBitmap = decodeBase64ToBitmap(photoBase64);
                    if (photoBitmap != null) {
                        ivPhoto.setImageBitmap(photoBitmap);
                        cardPhoto.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            // Generate Aadhaar Card Front View (without masking)
            generateAadhaarFront(data);
            
            // Generate Aadhaar Card Back View
            generateAadhaarBack(data);
        }
    }
    
    private String formatAadhaarNumber(String number) {
        if (number == null || number.length() != 12) {
            return number;
        }
        return number.substring(0, 4) + " " + number.substring(4, 8) + " " + number.substring(8, 12);
    }
    
    private Bitmap decodeBase64ToBitmap(String base64String) {
        try {
            // Remove data URI prefix if present
            String pureBase64 = base64String;
            if (base64String.contains(",")) {
                pureBase64 = base64String.substring(base64String.indexOf(",") + 1);
            }
            
            byte[] decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void generateAadhaarFront(AadhaarResponse.DataFromAadhaar data) {
        // For front card, we show the unmasked details
        // The actual implementation would create a card-like view
        // Here we're using a placeholder approach
        cardFront.setVisibility(View.VISIBLE);
        
        // The front card view is already shown with full Aadhaar number
        // (no masking as requested)
    }
    
    private void generateAadhaarBack(AadhaarResponse.DataFromAadhaar data) {
        // Back card shows address details
        cardBack.setVisibility(View.VISIBLE);
    }
}
