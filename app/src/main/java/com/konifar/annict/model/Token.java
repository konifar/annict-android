package com.konifar.annict.model;

import com.google.gson.annotations.SerializedName;

/**
 * https://annict.wikihub.io/wiki/api/authentication
 */
public class Token {

  @SerializedName("access_token") public String accessToken;

  @SerializedName("token_type") public String tokenType;

  @SerializedName("expires_in") public long expiresIn;

  @SerializedName("scope") public String scope;

  @SerializedName("created_at") public long createdAt;
}
