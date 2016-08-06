package com.konifar.annict.repository;

import com.konifar.annict.model.Status;

import rx.Observable;

public interface StatusRepository {

    Observable<Void> edit(long workId, Status status);
}
