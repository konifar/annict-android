package com.konifar.annict.api.builder;

import rx.Observable;

public interface Builder<T> {

    Observable<T> build();

}
