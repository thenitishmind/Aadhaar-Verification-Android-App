package com.karza.aadhaarverify.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class ApiClient {
    
    private static final String BASE_URL = "https://api.karza.in/";
    
    // IMPORTANT: Replace with your actual Karza API Key from https://karza.in
    // Get your API key from: Karza Dashboard -> API Keys section
    // This key is passed as 'x-karza-key' header in all API requests
    public static final String API_KEY = "YOUR_KARZA_API_KEY_HERE"; // TODO: Replace with your actual key
    
    private static Retrofit retrofit = null;
    private static KarzaApiService apiService = null;
    
    public static Retrofit getClient() {
        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
            
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        }
        return retrofit;
    }
    
    public static KarzaApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(KarzaApiService.class);
        }
        return apiService;
    }
}
