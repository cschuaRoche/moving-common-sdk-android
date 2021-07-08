package com.google.android.vending.licensing;

public class LicenseVerificationDTO {
    private String signedData;
    private String signature;

    public LicenseVerificationDTO(String signedData, String signature) {
        this.signedData = signedData;
        this.signature = signature;
    }

    public String getSignedData() {
        return signedData;
    }

    public void setSignedData(String signedData) {
        this.signedData = signedData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
