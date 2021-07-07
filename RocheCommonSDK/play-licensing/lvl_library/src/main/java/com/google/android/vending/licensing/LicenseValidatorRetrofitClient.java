package com.google.android.vending.licensing;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LicenseValidatorRetrofitClient {
    private final LicenseValidatorApiService licenseValidatorApiService;
    private static LicenseValidatorRetrofitClient sharedInstance;

    private LicenseValidatorRetrofitClient(String baseUrl) {
        Retrofit retrofit = createRetrofit(baseUrl);
        licenseValidatorApiService = retrofit.create(LicenseValidatorApiService.class);
    }

    public static LicenseValidatorRetrofitClient getInstance() {
        if (sharedInstance == null) {
            throw new AssertionError("Instance must be configured before use");
        }
        return sharedInstance;
    }

    public static void initialize(String baseUrl) {
        sharedInstance = new LicenseValidatorRetrofitClient(baseUrl);
    }

    private Retrofit createRetrofit(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder().baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    public void validateLicense(LicenseVerificationDTO licenseVerificationDTO, ServerLicenseValidatorCallback callback) {
        licenseValidatorApiService.verifyLicense(licenseVerificationDTO).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                callback.onServerResponse(response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onServerResponse(ServerLicenseValidatorCallback.ERROR);
            }
        });
    }
}
