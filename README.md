# 🆔 Aadhaar Verification Android App

A comprehensive Android application for verifying Aadhaar identity using the **Karza eAadhaar API**. This app provides a secure, user-friendly way to authenticate users via Aadhaar OTP verification and display complete Aadhaar details.

![Android](https://img.shields.io/badge/Platform-Android-green?logo=android)
![API Level](https://img.shields.io/badge/API-24%2B-brightgreen)
![Java](https://img.shields.io/badge/Language-Java-orange?logo=java)
![License](https://img.shields.io/badge/License-MIT-blue)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Project Architecture](#-project-architecture)
- [Technology Stack](#-technology-stack)
- [Prerequisites](#-prerequisites)
- [Installation & Setup](#-installation--setup)
- [Configuration](#-configuration)
- [Application Flow](#-application-flow)
- [API Documentation](#-api-documentation)
- [Project Structure](#-project-structure)
- [Screen Descriptions](#-screen-descriptions)
- [Data Models](#-data-models)
- [Building the APK](#-building-the-apk)
- [Security Considerations](#-security-considerations)
- [Troubleshooting](#-troubleshooting)

---

## 🔍 Overview

This application enables businesses and individuals to verify Aadhaar identity through a secure OTP-based verification process. The app integrates with **Karza's eAadhaar API** to:

1. Send OTP to the mobile number linked with Aadhaar
2. Verify the OTP along with a user-provided share code
3. Retrieve and display comprehensive Aadhaar details including photo, name, address, and more

The verification process follows UIDAI (Unique Identification Authority of India) guidelines and ensures data privacy through encrypted communications.

---

## ✨ Features

### Core Features
| Feature | Description |
|---------|-------------|
| 📱 **Aadhaar Input** | Enter 12-digit Aadhaar number with real-time validation |
| 📤 **OTP Generation** | Send OTP to registered mobile number via Karza API |
| 🔐 **OTP Verification** | Verify 6-digit OTP with 4-digit share code for security |
| 👤 **Profile Display** | Show complete Aadhaar holder information |

### Displayed Information
- **NAME**: Full name as registered in Aadhaar
- **ADDRESS**: Complete address with all components (House, Street, Locality, District, State, Pincode)
- **AADHAAR FRONT**: Full 12-digit Aadhaar number (unmasked for verification purposes)
- **AADHAAR BACK**: Detailed address breakdown
- **PHOTO**: Profile photograph from Aadhaar (decoded from Base64)
- **DOB**: Date of Birth
- **GENDER**: Gender as recorded
- **MOBILE STATUS**: Mobile linkage verification status

### Additional Features
- ⏱️ **OTP Timer**: 60-second countdown for OTP resend
- 🔄 **Resend OTP**: Option to resend OTP after timeout
- ✅ **Input Validation**: Real-time validation for all inputs
- 📊 **Loading States**: Progress indicators during API calls
- 🚫 **Error Handling**: Comprehensive error messages for all scenarios

---

## 🏗️ Project Architecture

The application follows a clean, modular architecture pattern:

```
┌─────────────────────────────────────────────────────────────────┐
│                        Presentation Layer                        │
│  ┌─────────────────┐ ┌────────────────────┐ ┌─────────────────┐ │
│  │  MainActivity   │ │OtpVerificationActivity│ │AadhaarDetailsActivity│ │
│  │  (Aadhaar Input)│ │   (OTP + Share Code)  │ │  (Display Data)      │ │
│  └────────┬────────┘ └──────────┬───────────┘ └────────────────┘ │
│           │                      │                                │
└───────────┼──────────────────────┼────────────────────────────────┘
            │                      │
┌───────────┼──────────────────────┼────────────────────────────────┐
│           │     Network Layer    │                                │
│           ▼                      ▼                                │
│  ┌─────────────────────────────────────────────────────────────┐ │
│  │                      ApiClient (Retrofit)                    │ │
│  │  ┌───────────────────────────────────────────────────────┐  │ │
│  │  │              KarzaApiService (Interface)               │  │ │
│  │  │  • sendOtp() → POST /v3/eaadhaar/otp                   │  │ │
│  │  │  • verifyOtp() → POST /v3/eaadhaar/file                │  │ │
│  │  └───────────────────────────────────────────────────────┘  │ │
│  └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
            │
┌───────────┼─────────────────────────────────────────────────────┐
│           │              Data Models Layer                       │
│           ▼                                                      │
│  ┌──────────────┐ ┌──────────────┐ ┌──────────────┐ ┌──────────┐│
│  │  OtpRequest  │ │  OtpResponse │ │VerifyRequest │ │AadhaarResponse││
│  └──────────────┘ └──────────────┘ └──────────────┘ └──────────┘│
└─────────────────────────────────────────────────────────────────┘
            │
            ▼
┌─────────────────────────────────────────────────────────────────┐
│                    External API (Karza)                          │
│              https://api.karza.in/v3/eaadhaar/                   │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🛠️ Technology Stack

| Category | Technology | Version | Purpose |
|----------|------------|---------|---------|
| **Language** | Java | 8 | Primary development language |
| **Platform** | Android SDK | 34 (Target) | Android development |
| **Min SDK** | Android | 24 (Android 7.0) | Minimum supported version |
| **Build Tool** | Gradle | 8.2.0 | Build automation |
| **Networking** | Retrofit | 2.9.0 | REST API client |
| **HTTP Client** | OkHttp | 4.12.0 | HTTP networking |
| **JSON Parsing** | Gson | 2.10.1 | JSON serialization/deserialization |
| **Image Loading** | Glide | 4.16.0 | Efficient image loading |
| **UI Components** | Material Design | 1.11.0 | Modern UI components |
| **Layout** | ConstraintLayout | 2.1.4 | Flexible layouts |
| **UI Binding** | ViewBinding | - | Type-safe view access |

---

## 📋 Prerequisites

Before setting up the project, ensure you have:

### Development Environment
- ✅ **Java Development Kit (JDK)** 8 or higher
- ✅ **Android Studio** Arctic Fox (2020.3.1) or later
- ✅ **Android SDK** with API level 34 installed
- ✅ **Gradle** 8.2.0 or compatible version

### API Requirements
- ✅ **Karza API Key**: Obtain from [Karza Technologies](https://karza.in)
- ✅ **Active Internet Connection** for API calls
- ✅ **Valid Test Aadhaar Numbers** (for sandbox testing)

### Device Requirements (For Testing)
- ✅ Android device or emulator running **Android 7.0 (API 24)** or higher
- ✅ Active internet connection on the test device

---

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone <repository-url>
cd android
```

### Step 2: Open in Android Studio

1. Launch **Android Studio**
2. Select **"Open an Existing Project"**
3. Navigate to the cloned `android` folder
4. Click **"OK"** and wait for Gradle sync to complete

### Step 3: Sync Gradle Dependencies

Android Studio will automatically sync dependencies. If not, manually sync:
- **File → Sync Project with Gradle Files**
- Or click the **"Sync Now"** link in the notification bar

### Step 4: Verify Build

```bash
# From the project root directory
.\gradlew build
```

---

## ⚙️ Configuration

### Configuring the Karza API Key

The API key is the most critical configuration. Follow these steps:

#### Method 1: Direct Configuration (Development Only)

1. Navigate to:
   ```
   app/src/main/java/com/karza/aadhaarverify/api/ApiClient.java
   ```

2. Locate the API_KEY constant:
   ```java
   public static final String API_KEY = "YOUR_KARZA_API_KEY_HERE";
   ```

3. Replace with your actual API key:
   ```java
   public static final String API_KEY = "your-actual-karza-api-key";
   ```

#### Method 2: Using BuildConfig (Recommended for Production)

1. Add to `local.properties` (do not commit this file):
   ```properties
   KARZA_API_KEY=your-actual-karza-api-key
   ```

2. Update `app/build.gradle`:
   ```gradle
   android {
       defaultConfig {
           buildConfigField "String", "KARZA_API_KEY", "\"${project.findProperty('KARZA_API_KEY') ?: ''}\""
       }
   }
   ```

3. Use in code:
   ```java
   public static final String API_KEY = BuildConfig.KARZA_API_KEY;
   ```

### Network Configuration

The app is configured to use HTTPS. The base URL is:
```
https://api.karza.in/
```

Timeout configurations (in `ApiClient.java`):
- **Connection Timeout**: 60 seconds
- **Read Timeout**: 60 seconds
- **Write Timeout**: 60 seconds

---

## 🔄 Application Flow

The application follows a three-step verification process:

### Flow Diagram

```
┌──────────────────────────────────────────────────────────────────────┐
│                                                                      │
│  ┌────────────────┐    ┌────────────────┐    ┌────────────────────┐  │
│  │                │    │                │    │                    │  │
│  │  STEP 1        │───▶│  STEP 2        │───▶│  STEP 3            │  │
│  │  Aadhaar Input │    │  OTP Verify    │    │  View Details      │  │
│  │                │    │                │    │                    │  │
│  └───────┬────────┘    └───────┬────────┘    └────────────────────┘  │
│          │                     │                                     │
│          ▼                     ▼                                     │
│  ┌────────────────┐    ┌────────────────┐                           │
│  │ Validate       │    │ Validate       │                           │
│  │ • 12 digits    │    │ • 6-digit OTP  │                           │
│  │ • Numeric only │    │ • 4+ share code│                           │
│  └────────────────┘    └────────────────┘                           │
│          │                     │                                     │
│          ▼                     ▼                                     │
│  ┌────────────────┐    ┌────────────────┐                           │
│  │ API Call:      │    │ API Call:      │                           │
│  │ Send OTP       │    │ Verify OTP     │                           │
│  │ /v3/eaadhaar/  │    │ /v3/eaadhaar/  │                           │
│  │ otp            │    │ file           │                           │
│  └────────────────┘    └────────────────┘                           │
│                                                                      │
└──────────────────────────────────────────────────────────────────────┘
```

### Detailed Steps

#### Step 1: Aadhaar Number Input (MainActivity)

1. User opens the app
2. User enters their 12-digit Aadhaar number
3. App validates the input in real-time:
   - Must be exactly 12 digits
   - Must contain only numeric characters
4. User taps "Send OTP"
5. App calls Karza API to send OTP to linked mobile
6. On success, navigates to OTP verification screen

#### Step 2: OTP Verification (OtpVerificationActivity)

1. User receives OTP on their registered mobile
2. User enters the 6-digit OTP
3. User enters a 4-digit share code (user-defined password)
4. App validates both inputs
5. 60-second timer runs for resend option
6. User taps "Verify"
7. App calls Karza API to verify OTP and retrieve data
8. On success, navigates to details screen

#### Step 3: View Aadhaar Details (AadhaarDetailsActivity)

1. App displays retrieved Aadhaar information
2. Shows:
   - Profile photo (decoded from Base64)
   - Full name
   - Complete address
   - Date of birth
   - Gender
   - Full Aadhaar number (unmasked)
   - Mobile linkage status
3. Front and back card views are generated

---

## 📡 API Documentation

### Karza eAadhaar API Overview

The app uses two main API endpoints:

### 1. Send OTP Endpoint

**Endpoint**: `POST https://api.karza.in/v3/eaadhaar/otp`

**Purpose**: Initiates OTP request to the mobile number linked with Aadhaar

**Headers**:
| Header | Value | Description |
|--------|-------|-------------|
| `x-karza-key` | `<API_KEY>` | Your Karza API authentication key |
| `Content-Type` | `application/json` | Request body format |

**Request Body**:
```json
{
  "aadhaarNo": "123456789012",
  "consent": "Y",
  "clientData": {
    "caseId": "case_1234567890"
  }
}
```

**Request Parameters**:
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `aadhaarNo` | String | Yes | 12-digit Aadhaar number |
| `consent` | String | Yes | User consent ("Y" for yes) |
| `clientData.caseId` | String | No | Unique identifier for tracking |

**Success Response** (Status Code: 101):
```json
{
  "statusCode": 101,
  "requestId": "req_abc123",
  "result": {
    "message": "OTP sent successfully",
    "accessKey": "access_key_xyz789"
  }
}
```

**Error Response**:
```json
{
  "statusCode": 102,
  "error": "Invalid Aadhaar number",
  "requestId": "req_abc123"
}
```

### 2. Verify OTP & Get Data Endpoint

**Endpoint**: `POST https://api.karza.in/v3/eaadhaar/file`

**Purpose**: Verifies OTP and retrieves complete Aadhaar data

**Headers**:
| Header | Value | Description |
|--------|-------|-------------|
| `x-karza-key` | `<API_KEY>` | Your Karza API authentication key |
| `Content-Type` | `application/json` | Request body format |

**Request Body**:
```json
{
  "accessKey": "access_key_xyz789",
  "otp": "123456",
  "aadhaarNo": "123456789012",
  "consent": "Y",
  "shareCode": "1234"
}
```

**Request Parameters**:
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| `accessKey` | String | Yes | Access key received from OTP request |
| `otp` | String | Yes | 6-digit OTP received on mobile |
| `aadhaarNo` | String | Yes | 12-digit Aadhaar number |
| `consent` | String | Yes | User consent ("Y" for yes) |
| `shareCode` | String | Yes | 4-digit user-defined password |

**Success Response** (Status Code: 101):
```json
{
  "statusCode": 101,
  "requestId": "req_def456",
  "result": {
    "dataFromAadhaar": {
      "name": "John Doe",
      "dob": "15-08-1990",
      "gender": "Male",
      "mobileHash": "abc123...",
      "emailHash": "def456...",
      "address": {
        "careOf": "S/O Father Name",
        "house": "123",
        "street": "Main Street",
        "landmark": "Near Temple",
        "locality": "Sector 15",
        "vtc": "City Name",
        "subdist": "Sub District",
        "district": "District Name",
        "state": "State Name",
        "pincode": "123456",
        "country": "India"
      },
      "maskedAadhaarNumber": "XXXX XXXX 9012",
      "photo": "<base64_encoded_image>",
      "generatedAt": "2024-01-15T10:30:00"
    },
    "file": {
      "pdfContent": "<base64_encoded_pdf>",
      "xmlContent": "<base64_encoded_xml>"
    }
  }
}
```

### API Status Codes

| Code | Meaning | Action |
|------|---------|--------|
| 101 | Success | Proceed with data |
| 102 | Invalid Aadhaar | Show error to user |
| 103 | OTP Expired | Request new OTP |
| 104 | Invalid OTP | Ask user to retry |
| 105 | Rate Limited | Wait and retry |

---

## 📁 Project Structure

```
android/
├── 📄 build.gradle                 # Root build configuration
├── 📄 settings.gradle              # Project settings & module inclusion
├── 📄 gradle.properties            # Gradle configuration properties
├── 📄 local.properties             # Local SDK path (git-ignored)
├── 📄 gradlew                      # Gradle wrapper script (Unix)
├── 📄 gradlew.bat                  # Gradle wrapper script (Windows)
├── 📄 README.md                    # This documentation file
│
├── 📁 gradle/
│   └── 📁 wrapper/
│       └── 📄 gradle-wrapper.properties  # Gradle wrapper version
│
└── 📁 app/
    ├── 📄 build.gradle             # App module build configuration
    ├── 📄 proguard-rules.pro       # ProGuard obfuscation rules
    │
    └── 📁 src/main/
        ├── 📄 AndroidManifest.xml  # App manifest & permissions
        │
        ├── 📁 java/com/karza/aadhaarverify/
        │   │
        │   ├── 📄 MainActivity.java              # Aadhaar input screen
        │   ├── 📄 OtpVerificationActivity.java   # OTP verification screen
        │   ├── 📄 AadhaarDetailsActivity.java    # Details display screen
        │   │
        │   ├── 📁 api/
        │   │   ├── 📄 ApiClient.java             # Retrofit client setup
        │   │   └── 📄 KarzaApiService.java       # API interface definitions
        │   │
        │   └── 📁 model/
        │       ├── 📄 OtpRequest.java            # OTP request model
        │       ├── 📄 OtpResponse.java           # OTP response model
        │       ├── 📄 VerifyRequest.java         # Verify request model
        │       └── 📄 AadhaarResponse.java       # Aadhaar data model
        │
        └── 📁 res/
            ├── 📁 layout/
            │   ├── 📄 activity_main.xml              # Aadhaar input layout
            │   ├── 📄 activity_otp_verification.xml  # OTP screen layout
            │   └── 📄 activity_aadhaar_details.xml   # Details screen layout
            │
            ├── 📁 values/                 # Colors, strings, themes
            ├── 📁 drawable/               # Icons and graphics
            └── 📁 mipmap-*/               # App icons (various densities)
```

---

## 📱 Screen Descriptions

### Screen 1: MainActivity (Aadhaar Input)

**File**: `MainActivity.java`  
**Layout**: `activity_main.xml`

**Purpose**: Entry point for Aadhaar verification

**UI Components**:
| Component | Type | ID | Function |
|-----------|------|-----|----------|
| Aadhaar Input | EditText | `etAadhaar` | 12-digit number input |
| Send OTP Button | Button | `btnSendOtp` | Initiate OTP request |
| Progress Indicator | ProgressBar | `progressBar` | Loading state display |

**Validation Logic**:
- Aadhaar must be exactly 12 digits
- Only numeric characters allowed
- Button enabled only when valid

**Key Methods**:
```java
sendOtp()           // Makes API call to send OTP
showLoading(bool)   // Toggles loading state
setupListeners()    // Sets up text watchers and click listeners
```

---

### Screen 2: OtpVerificationActivity

**File**: `OtpVerificationActivity.java`  
**Layout**: `activity_otp_verification.xml`

**Purpose**: Verify OTP and share code

**UI Components**:
| Component | Type | ID | Function |
|-----------|------|-----|----------|
| OTP Input | EditText | `etOtp` | 6-digit OTP entry |
| Share Code Input | EditText | `etShareCode` | 4-digit password |
| Verify Button | Button | `btnVerify` | Submit verification |
| Resend Button | Button | `btnResend` | Resend OTP |
| Timer Display | TextView | `tvTimer` | Countdown timer |
| Aadhaar Display | TextView | `tvAadhaarDisplay` | Shows masked Aadhaar |
| Progress Indicator | ProgressBar | `progressBar` | Loading state |

**Features**:
- 60-second countdown timer for resend
- Masked Aadhaar number display (XXXX XXXX 9012)
- Real-time input validation

**Key Methods**:
```java
verifyOtp()                      // Makes verification API call
startTimer()                     // Starts 60-second countdown
serializeAadhaarData(response)   // Converts response to JSON for passing
```

---

### Screen 3: AadhaarDetailsActivity

**File**: `AadhaarDetailsActivity.java`  
**Layout**: `activity_aadhaar_details.xml`

**Purpose**: Display verified Aadhaar information

**UI Components**:
| Component | Type | ID | Function |
|-----------|------|-----|----------|
| Photo | ImageView | `ivPhoto` | Profile photograph |
| Name | TextView | `tvName` | Full name display |
| Address | TextView | `tvAddress` | Complete address |
| Aadhaar Number | TextView | `tvAadhaarNumber` | Unmasked number |
| DOB | TextView | `tvDob` | Date of birth |
| Gender | TextView | `tvGender` | Gender display |
| Mobile Status | TextView | `tvMobile` | Linkage status |
| Photo Card | CardView | `cardPhoto` | Photo container |
| Front Card | CardView | `cardFront` | Aadhaar front view |
| Back Card | CardView | `cardBack` | Aadhaar back view |

**Key Methods**:
```java
displayData()                    // Populates all UI fields
formatAadhaarNumber(String)      // Formats as XXXX XXXX XXXX
decodeBase64ToBitmap(String)     // Decodes photo from Base64
generateAadhaarFront(data)       // Creates front card view
generateAadhaarBack(data)        // Creates back card view
```

---

## 📊 Data Models

### OtpRequest

**Purpose**: Request body for sending OTP

```java
public class OtpRequest {
    String aadhaarNo;              // 12-digit Aadhaar number
    String consent;                 // "Y" for user consent
    ClientData clientData;          // Additional tracking data
    
    static class ClientData {
        String caseId;              // Auto-generated unique ID
    }
}
```

### OtpResponse

**Purpose**: Response from OTP send request

```java
public class OtpResponse {
    int statusCode;                 // 101 = success
    String requestId;               // Unique request identifier
    String error;                   // Error message (if failed)
    Result result;                  // Success data
    
    static class Result {
        String message;             // Success message
        String accessKey;           // Key for verification step
    }
    
    boolean isSuccess();            // Returns statusCode == 101
}
```

### VerifyRequest

**Purpose**: Request body for OTP verification

```java
public class VerifyRequest {
    String accessKey;               // From OtpResponse
    String otp;                     // 6-digit OTP from user
    String aadhaarNo;               // 12-digit Aadhaar number
    String consent;                 // "Y" for user consent
    String shareCode;               // 4-digit user password
}
```

### AadhaarResponse

**Purpose**: Complete Aadhaar data response

```java
public class AadhaarResponse {
    int statusCode;                 // 101 = success
    String requestId;               // Unique request identifier
    String error;                   // Error message (if failed)
    Result result;                  // Aadhaar data
    
    static class Result {
        DataFromAadhaar dataFromAadhaar;  // Personal details
        FileData file;                     // PDF/XML content
    }
    
    static class DataFromAadhaar {
        String name;                // Full name
        String dob;                 // Date of birth
        String gender;              // Gender
        String mobileHash;          // Mobile verification hash
        String emailHash;           // Email verification hash
        Address address;            // Complete address object
        String maskedAadhaarNumber; // Masked number from API
        String photo;               // Base64 encoded photo
        String generatedAt;         // Timestamp
        
        String getFullAddress();    // Formatted complete address
    }
    
    static class Address {
        String careOf;              // Care of (S/O, D/O, etc.)
        String house;               // House/Building number
        String street;              // Street name
        String landmark;            // Nearby landmark
        String locality;            // Locality/Area
        String vtc;                 // Village/Town/City
        String subdist;             // Sub-district
        String district;            // District name
        String state;               // State name
        String pincode;             // 6-digit PIN code
        String country;             // Country (India)
        String postOffice;          // Post office name
    }
    
    static class FileData {
        String pdfContent;          // Base64 encoded PDF
        String xmlContent;          // Base64 encoded XML
    }
}
```

---

## 🔨 Building the APK

### Using Android Studio (Recommended)

#### Debug Build:
1. Open the project in Android Studio
2. Navigate to **Build → Build Bundle(s) / APK(s) → Build APK(s)**
3. Wait for the build to complete
4. Click **"locate"** in the notification to find the APK

**Output Location**:
```
app/build/outputs/apk/debug/app-debug.apk
```

#### Release Build:
1. Navigate to **Build → Generate Signed Bundle / APK**
2. Select **APK** and click **Next**
3. Create or select a keystore
4. Enter keystore credentials
5. Select **release** build variant
6. Click **Finish**

**Output Location**:
```
app/build/outputs/apk/release/app-release.apk
```

### Using Command Line

#### On Windows:

```batch
cd android

REM Debug build
.\gradlew.bat assembleDebug

REM Release build (requires signing config)
.\gradlew.bat assembleRelease

REM Build both
.\gradlew.bat assemble

REM Clean and build
.\gradlew.bat clean assembleDebug
```

#### On macOS/Linux:

```bash
cd android

# Debug build
./gradlew assembleDebug

# Release build (requires signing config)
./gradlew assembleRelease

# Build both
./gradlew assemble

# Clean and build
./gradlew clean assembleDebug
```

### Build Variants

| Variant | Use Case | Signing | ProGuard |
|---------|----------|---------|----------|
| **Debug** | Development & Testing | Debug keystore | Disabled |
| **Release** | Production | Custom keystore | Enabled |

---

## 🔒 Security Considerations

### API Key Security

⚠️ **IMPORTANT**: Never commit API keys to version control!

**Best Practices**:
1. Use `local.properties` for local development (already in `.gitignore`)
2. Use environment variables in CI/CD pipelines
3. Consider using Android Keystore for production apps
4. Implement certificate pinning for added security

### Data Privacy

The app handles sensitive personal data. Ensure:

1. ✅ **HTTPS Only**: All API calls use HTTPS
2. ✅ **No Local Storage**: Aadhaar data is not persisted locally
3. ✅ **User Consent**: Consent flag is mandatory for all API calls
4. ✅ **Share Code**: Adds an extra layer of security
5. ✅ **Session-based**: Access keys expire after use

### Network Security

```xml
<!-- Already configured in AndroidManifest.xml -->
<application
    android:usesCleartextTraffic="false"
    ...>
```

### Recommended Additional Security Measures

1. **ProGuard/R8**: Enable code obfuscation for release builds
2. **Root Detection**: Consider blocking rooted devices
3. **SSL Pinning**: Pin Karza's SSL certificate
4. **Timeout Handling**: Implement session timeouts

---

## ❓ Troubleshooting

### Common Issues and Solutions

#### 1. Build Failure: "Could not resolve dependencies"

**Solution**:
```batch
.\gradlew.bat clean
.\gradlew.bat build --refresh-dependencies
```

#### 2. API Error: "Invalid API Key"

**Solution**:
- Verify API key is correctly placed in `ApiClient.java`
- Check for extra spaces or characters
- Confirm API key is active on Karza dashboard

#### 3. Network Error: "Unable to resolve host"

**Solution**:
- Check device internet connection
- Verify INTERNET permission in manifest
- Check if running on emulator with network access

#### 4. OTP Not Received

**Possible Causes**:
- Mobile number not linked to Aadhaar
- DND (Do Not Disturb) enabled
- Network issues with telecom operator

#### 5. Photo Not Displaying

**Solution**:
- Check if photo Base64 string is valid
- Verify Base64 decoding logic
- Check image size limits

#### 6. Gradle Sync Failed

**Solution**:
1. **File → Invalidate Caches and Restart**
2. Delete `.gradle` folder and re-sync
3. Update Gradle wrapper version

### Debug Logging

Enable verbose logging by checking Logcat with filter:
```
OkHttp
```

This will show all API requests and responses.

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 🤝 Support

For issues related to:
- **This Application**: Open a GitHub issue
- **Karza API**: Contact [Karza Support](https://karza.in/contact)
- **Android Development**: Refer to [Android Developer Documentation](https://developer.android.com)

---

## 📝 Changelog

### Version 1.0.0
- Initial release
- Aadhaar OTP verification
- Full details display
- Photo decoding support

---

*Built with ❤️ for secure Aadhaar verification*
