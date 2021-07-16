package com.google.android.vending.licensing;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LicenseValidatorApiService {
    @POST("google-license-verf")
    Call<Void> verifyLicense(@Body LicenseVerificationDTO licenseVerificationDTO);
}
