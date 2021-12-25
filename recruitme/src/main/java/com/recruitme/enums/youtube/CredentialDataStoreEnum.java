package com.recruitme.enums.youtube;

public enum CredentialDataStoreEnum {
    UPLOAD_VIDEO("uploadvideo");

    private String credentialDataStore;
    CredentialDataStoreEnum(String credentialDataStore) { this.credentialDataStore=credentialDataStore; }
    public String getCredentialDataStore() { return this.credentialDataStore; }
}
