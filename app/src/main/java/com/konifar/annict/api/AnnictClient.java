package com.konifar.annict.api;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.konifar.annict.BuildConfig;
import com.konifar.annict.model.Programs;
import com.konifar.annict.model.Token;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    public static String getOAuthUrl() {
        Uri uri = Uri.parse(BASE_URI + "/oauth/authorize")
                .buildUpon()
                .appendQueryParameter("client_id", BuildConfig.ANNICT_APPLICATION_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("redirect_uri", OAUTH_REDIRECT_URI)
                .build();
        return uri.toString();
    }

    public Observable<Token> postOauthToken(@NonNull String authCode) {
        return service.postOauthToken(BuildConfig.ANNICT_APPLICATION_ID,
                BuildConfig.ANNICT_SECRET_KEY,
                "authorization_code",
                OAUTH_REDIRECT_URI,
                authCode);
    }

    public Observable<Programs> getMePrograms(int page) {
        return service.getMeProgarms(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                page,
                50,
                null,
                Sort.DESC.toString());
    }

    private enum Sort {
        ASC, DESC;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    public interface AnnictService {

        /**
         * https://annict.wikihub.io/wiki/api/authentication
         */
        @POST("/oauth/token")
        Observable<Token> postOauthToken(@Query("client_id") String clientId,
                                         @Query("client_secret") String clientSecret,
                                         @Query("grant_type") String grantType,
                                         @Query("redirect_uri") String scope,
                                         @Query("code") String code);

        /**
         * https://annict.wikihub.io/wiki/api/me-programs
         */
        @GET("/v1/me/programs")
        Observable<Programs> getMeProgarms(@Query("fields") @Nullable String fields,
                                           @Query("filter_ids") @Nullable String filterIds,
                                           @Query("filter_channel_ids") @Nullable String filterChannelIds,
                                           @Query("filter_work_ids") @Nullable String filterWorkIds,
                                           @Query("filter_started_at_gt") @Nullable String filterStartedAtGt,
                                           @Query("filter_started_at_lt") @Nullable String filterStartedAtLt,
                                           @Query("filter_unwatched") @Nullable Boolean filterUnwatched,
                                           @Query("filter_rebroadcast") @Nullable Boolean filterRebroadcast,
                                           @Query("page") int page,
                                           @Query("per_page") int perPage,
                                           @Query("sort_id") @Nullable String sortId,
                                           @Query("sort_started_at") @Nullable String sortStartedAt);

    }

}
