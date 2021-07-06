package com.google.android.vending.licensing;

public class LicenseVerificationDTO {
    private String signature;
    private String signedData;

    public LicenseVerificationDTO(String signature, String signedData) {
        this.signature = signature;
        this.signedData = signedData;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignedData() {
        return signedData;
    }

    public void setSignedData(String signedData) {
        this.signedData = signedData;
    }
}
