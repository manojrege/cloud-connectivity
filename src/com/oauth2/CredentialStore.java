package com.oauth2;



import com.google.api.client.auth.oauth2.draft10.AccessTokenResponse;

public interface CredentialStore {

  AccessTokenResponse read();
  void write(AccessTokenResponse response);
  void clearCredentials();
}