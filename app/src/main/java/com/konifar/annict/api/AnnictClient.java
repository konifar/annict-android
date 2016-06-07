package com.konifar.annict.api;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.konifar.annict.BuildConfig;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

@Singleton
public class AnnictClient {

    private static final String BASE_URI = "https://api.annict.com";
    private static final String OAUTH_REDIRECT_URI = "intent://annict-android/authorize";

    private final AnnictService service;

    @Inject
    public AnnictClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URI)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .build();
        service = retrofit.create(AnnictService.class);
    }

    public static Gson createGson() {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public String getOAuthUrl() {
        Uri uri = Uri.parse(BASE_URI + "/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.ANNICT_APPLICATION_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("redirect_uri", OAUTH_REDIRECT_URI)
                .build();
        return uri.toString();
    }

    public interface AnnictService {

        @GET("/oauth/authorize")
        Observable<Object> getOauthAuthorize(@Query("client_id") String clientId,
                                             @Query("response_type") String responseType,
                                             @Query("redirect_uri") String redirectUri,
                                             @Query("scope") String scope);

//        @GET(SESSIONS_API_ROUTES + "sessions_ja.json")
//        Observable<List<Session>> getSessionsJa();
//
//        @GET(SESSIONS_API_ROUTES + "sessions_en.json")
//        Observable<List<Session>> getSessionsEn();
//
//        @GET(SESSIONS_API_ROUTES + "sessions_ko.json")
//        Observable<List<Session>> getSessionsKo();
//
//        @GET(SESSIONS_API_ROUTES + "sessions_ar.json")
//        Observable<List<Session>> getSessionsAr();

    }

}
