package com.google.android.vending.licensing;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LicenseValidatorRetrofitClient {
    private static final String BASE_URL = "https://api-passport.tpp1-dev.platform.navify.com/passport/v1/";

    private final LicenseValidatorApiService licenseValidatorApiService;
    private static LicenseValidatorRetrofitClient sharedInstance;

    private LicenseValidatorRetrofitClient() {
        Retrofit retrofit = createRetrofit();
        licenseValidatorApiService = retrofit.create(LicenseValidatorApiService.class);
    }

    public static LicenseValidatorRetrofitClient getInstance() {
        if (sharedInstance == null) {
            throw new AssertionError("Instance must be configured before use");
        }
        return sharedInstance;
    }

    public static void initialize() {
        sharedInstance = new LicenseValidatorRetrofitClient();
    }

    private Retrofit createRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
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
