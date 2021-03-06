package com.konifar.annict.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.model.Programs;
import com.konifar.annict.model.Record;
import com.konifar.annict.model.Token;
import com.konifar.annict.model.Works;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

@Singleton
public class AnnictClient {

    private static final String BASE_URI = "https://api.annict.com";

    private static final String OAUTH_REDIRECT_URI = "intent://annict-android/authorize";

    public final AnnictService service;

    @Inject
    public AnnictClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder().client(client)
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
        return service.postOauthToken(BuildConfig.ANNICT_APPLICATION_ID, BuildConfig.ANNICT_SECRET_KEY,
            "authorization_code", OAUTH_REDIRECT_URI, authCode);
    }

    public interface AnnictService {

        /**
         * https://annict.wikihub.io/wiki/api/authentication
         */
        @POST("/oauth/token")
        Observable<Token> postOauthToken(
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret, @Query("grant_type") String grantType,
            @Query("redirect_uri") String scope, @Query("code") String code
        );

        /**
         * https://annict.wikihub.io/wiki/api/me-programs
         */
        @GET("/v1/me/programs")
        Observable<Programs> getMeProgarms(
            @Query("fields") @Nullable String fields, @Query("filter_ids") @Nullable String filterIds,
            @Query("filter_channel_ids") @Nullable String filterChannelIds,
            @Query("filter_work_ids") @Nullable String filterWorkIds,
            @Query("filter_started_at_gt") @Nullable String filterStartedAtGt,
            @Query("filter_started_at_lt") @Nullable String filterStartedAtLt,
            @Query("filter_unwatched") @Nullable Boolean filterUnwatched,
            @Query("filter_rebroadcast") @Nullable Boolean filterRebroadcast, @Query("page") int page,
            @Query("per_page") int perPage, @Query("sort_id") @Nullable String sortId,
            @Query("sort_started_at") @Nullable String sortStartedAt
        );

        /**
         * https://annict.wikihub.io/wiki/api/me-works
         */
        @GET("/v1/me/works")
        Observable<Works> getMeWorks(
            @Query("fields") @Nullable String fields,
            @Query("filter_ids") @Nullable String filterIds,
            @Query("filter_season") @Nullable String filterSeason,
            @Query("filter_title") @Nullable String filterTitle,
            @Query("filter_status") @Nullable String filterStatus, @Query("page") int page,
            @Query("per_page") int perPage, @Query("sort_id") @Nullable String sortId,
            @Query("sort_reason") @Nullable String sortReason,
            @Query("sort_watchers_count") @Nullable String sortWatchersCount
        );

        /**
         * https://annict.wikihub.io/wiki/api/me-statuses
         */
        @POST("/v1/me/statuses")
        Observable<Void> postMeStatuses(
            @Query("work_id") long workId,
            @Query("kind") @NonNull String kind
        );

        /**
         * https://annict.wikihub.io/wiki/api/works
         */
        @GET("/v1/works")
        Observable<Works> getWorks(
            @Query("fields") @Nullable String fields,
            @Query("filter_ids") @Nullable String filterIds,
            @Query("filter_season") @Nullable String filterSeason,
            @Query("filter_title") @Nullable String filterTitle, @Query("page") int page,
            @Query("per_page") int perPage, @Query("sort_id") @Nullable String sortId,
            @Query("sort_reason") @Nullable String sortReason,
            @Query("sort_watchers_count") @Nullable String sortWatchersCount
        );

        /**
         * https://annict.wikihub.io/wiki/api/me-records
         */
        @POST("/v1/me/records")
        Observable<Record> postMeRecords(
            @Query("episode_id") @NonNull Long episodeId,
            @Query("comment") String comment,
            @Query("rating") Float rating,
            @Query("share_twitter") boolean shareTwitter,
            @Query("share_facebook") boolean shareFacebook
        );

        /**
         * https://annict.wikihub.io/wiki/api/me-records
         */
        @PUT("/v1/me/records/{record_id}")
        Observable<Record> putMeRecords(
            @Path("record_id") @NonNull Long recordId,
            @Query("comment") String comment,
            @Query("rating") Float rating,
            @Query("share_twitter") boolean shareTwitter,
            @Query("share_facebook") boolean shareFacebook
        );
    }
}
