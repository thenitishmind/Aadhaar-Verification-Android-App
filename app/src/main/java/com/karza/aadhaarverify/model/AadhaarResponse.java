package com.karza.aadhaarverify.model;

import com.google.gson.annotations.SerializedName;

public class AadhaarResponse {
    
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
        @SerializedName("dataFromAadhaar")
        private DataFromAadhaar dataFromAadhaar;
        
        @SerializedName("file")
        private FileData file;
        
        public DataFromAadhaar getDataFromAadhaar() {
            return dataFromAadhaar;
        }
        
        public FileData getFile() {
            return file;
        }
    }
    
    public static class DataFromAadhaar {
        @SerializedName("name")
        private String name;
        
        @SerializedName("dob")
        private String dob;
        
        @SerializedName("gender")
        private String gender;
        
        @SerializedName("mobileHash")
        private String mobileHash;
        
        @SerializedName("emailHash")
        private String emailHash;
        
        @SerializedName("address")
        private Address address;
        
        @SerializedName("maskedAadhaarNumber")
        private String maskedAadhaarNumber;
        
        @SerializedName("photo")
        private String photo;
        
        @SerializedName("generatedAt")
        private String generatedAt;
        
        public String getName() {
            return name;
        }
        
        public String getDob() {
            return dob;
        }
        
        public String getGender() {
            return gender;
        }
        
        public String getMobileHash() {
            return mobileHash;
        }
        
        public String getEmailHash() {
            return emailHash;
        }
        
        public Address getAddress() {
            return address;
        }
        
        public String getMaskedAadhaarNumber() {
            return maskedAadhaarNumber;
        }
        
        public String getPhoto() {
            return photo;
        }
        
        public String getGeneratedAt() {
            return generatedAt;
        }
        
        public String getFullAddress() {
            if (address == null) return "";
            StringBuilder sb = new StringBuilder();
            
            if (address.getCareOf() != null && !address.getCareOf().isEmpty()) {
                sb.append("C/O: ").append(address.getCareOf()).append("\n");
            }
            if (address.getHouse() != null && !address.getHouse().isEmpty()) {
                sb.append(address.getHouse()).append(", ");
            }
            if (address.getStreet() != null && !address.getStreet().isEmpty()) {
                sb.append(address.getStreet()).append(", ");
            }
            if (address.getLandmark() != null && !address.getLandmark().isEmpty()) {
                sb.append(address.getLandmark()).append(", ");
            }
            if (address.getLocality() != null && !address.getLocality().isEmpty()) {
                sb.append(address.getLocality()).append("\n");
            }
            if (address.getVtc() != null && !address.getVtc().isEmpty()) {
                sb.append(address.getVtc()).append(", ");
            }
            if (address.getSubdist() != null && !address.getSubdist().isEmpty()) {
                sb.append(address.getSubdist()).append("\n");
            }
            if (address.getDistrict() != null && !address.getDistrict().isEmpty()) {
                sb.append(address.getDistrict()).append(", ");
            }
            if (address.getState() != null && !address.getState().isEmpty()) {
                sb.append(address.getState()).append(" - ");
            }
            if (address.getPincode() != null && !address.getPincode().isEmpty()) {
                sb.append(address.getPincode());
            }
            
            return sb.toString().trim();
        }
    }
    
    public static class Address {
        @SerializedName("careOf")
        private String careOf;
        
        @SerializedName("country")
        private String country;
        
        @SerializedName("district")
        private String district;
        
        @SerializedName("house")
        private String house;
        
        @SerializedName("landmark")
        private String landmark;
        
        @SerializedName("locality")
        private String locality;
        
        @SerializedName("pincode")
        private String pincode;
        
        @SerializedName("postOffice")
        private String postOffice;
        
        @SerializedName("state")
        private String state;
        
        @SerializedName("street")
        private String street;
        
        @SerializedName("subdist")
        private String subdist;
        
        @SerializedName("vtc")
        private String vtc;
        
        public String getCareOf() {
            return careOf;
        }
        
        public String getCountry() {
            return country;
        }
        
        public String getDistrict() {
            return district;
        }
        
        public String getHouse() {
            return house;
        }
        
        public String getLandmark() {
            return landmark;
        }
        
        public String getLocality() {
            return locality;
        }
        
        public String getPincode() {
            return pincode;
        }
        
        public String getPostOffice() {
            return postOffice;
        }
        
        public String getState() {
            return state;
        }
        
        public String getStreet() {
            return street;
        }
        
        public String getSubdist() {
            return subdist;
        }
        
        public String getVtc() {
            return vtc;
        }
    }
    
    public static class FileData {
        @SerializedName("pdfContent")
        private String pdfContent;
        
        @SerializedName("xmlContent")
        private String xmlContent;
        
        public String getPdfContent() {
            return pdfContent;
        }
        
        public String getXmlContent() {
            return xmlContent;
        }
    }
}
