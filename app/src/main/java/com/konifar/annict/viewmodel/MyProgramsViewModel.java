package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Programs;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.viewmodel.event.EventBus;
import com.konifar.annict.viewmodel.event.MyProgramsLoadedEvent;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MyProgramsViewModel implements ViewModel {

    private static final String TAG = MyProgramsViewModel.class.getSimpleName();

    private final Context context;
    private final AnnictClient client;
    private final EventBus eventBus;
    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    public ObservableInt progressBarVisibility = new ObservableInt(View.GONE);
    public ObservableInt recyclerViewVisibility = new ObservableInt(View.GONE);

    @Inject
    public MyProgramsViewModel(Context context,
                               AnnictClient client,
                               EventBus eventBus) {
        this.context = context;
        this.client = client;
        this.eventBus = eventBus;
    }

    public void showPrograms(@Nullable String accessToken, @NonNull String authCode) {
        Subscription sub = getLoadObservable(accessToken, authCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> progressBarVisibility.set(View.VISIBLE))
                .doOnCompleted(() -> progressBarVisibility.set(View.GONE))
                .doOnError((throwable) -> progressBarVisibility.set(View.GONE))
                .map(programs ->
                        Stream.of(programs.list)
                                .map(MyProgramItemViewModel::new)
                                .collect(Collectors.toList())
                )
                .subscribe(
                        programViewModels -> {
                            recyclerViewVisibility.set(View.VISIBLE);
                            eventBus.post(new MyProgramsLoadedEvent(programViewModels));
                        },
                        throwable -> {
                            progressBarVisibility.set(View.GONE);
                            Log.e(TAG, "load auth token error occurred.", throwable);
                        }
                );

        compositeSubscription.add(sub);
    }

    private Observable<Programs> getLoadObservable(@Nullable String accessToken, @NonNull String authCode) {
        if (!TextUtils.isEmpty(accessToken)) {
            return client.getMePrograms(1);
        } else {
            return client.postOauthToken(authCode)
                    .flatMap(token -> {
                        DefaultPrefs.get(context).putAccessToken(token.accessToken);
                        return client.getMePrograms(1);
                    });
        }
    }

    @Override
    public void destroy() {
        compositeSubscription.unsubscribe();
    }

}
