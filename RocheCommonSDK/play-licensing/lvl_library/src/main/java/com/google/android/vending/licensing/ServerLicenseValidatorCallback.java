package com.google.android.vending.licensing;

public interface ServerLicenseValidatorCallback {
    int SUCCESS = 200;
    int UNAUTHORISED_ACCESS = 401;
    int ERROR = -1;

    void onServerResponse(int response);
}
