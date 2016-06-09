package com.konifar.annict.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.konifar.annict.api.RequestInterceptor;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import rx.subscriptions.CompositeSubscription;

@Module
public class AppModule {

    static final String CACHE_FILE_NAME = "okhttp.cache";
    static final long MAX_CACHE_SIZE = 4 * 1024 * 1024;
    static final String SHARED_PREF_NAME = "preferences";

    private Context context;

    public AppModule(Application app) {
        context = app;
    }

    @Provides
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public OkHttpClient provideHttpClient(Context context, Interceptor interceptor) {
        File cacheDir = new File(context.getCacheDir(), CACHE_FILE_NAME);
        Cache cache = new Cache(cacheDir, MAX_CACHE_SIZE);

        OkHttpClient.Builder c = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(interceptor);

        return c.build();
    }

    @Provides
    public Interceptor provideRequestInterceptor(Context context) {
        return new RequestInterceptor(context);
    }

    @Provides
    public SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    public CompositeSubscription provideCompositeSubscription() {
        return new CompositeSubscription();
    }

}
