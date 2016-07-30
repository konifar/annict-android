package com.konifar.annict.view.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jakewharton.rxbinding.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding.support.v7.widget.RxRecyclerView;

import rx.Observable;
import rx.Subscription;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

public class RxRecyclerViewScrollSubject {

    private final int offset = 2;

    private final Subject<RecyclerViewScrollEvent, RecyclerViewScrollEvent> subject = PublishSubject.create();

    public Observable<RecyclerViewScrollEvent> observable() {
        return subject;
    }

    private boolean isLoading;

    public Subscription subscribeScrollEvent(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        return RxRecyclerView.scrollEvents(recyclerView).subscribe(event -> {
            int totalItemCount = linearLayoutManager.getItemCount();
            int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
            if (!isLoading && totalItemCount - 1 - offset <= lastVisibleItemPosition) {
                isLoading = true;
                subject.onNext(event);
            }
        });
    }

    public void complete() {
        isLoading = false;
    }
}
