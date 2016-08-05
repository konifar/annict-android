package com.konifar.annict.repository;

import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;

import java.util.List;

import rx.Observable;

public interface WorkRepository {

    Observable<List<Work>> getMineWhereStatusWithAuth(String authCode, Status status, int page);

    Observable<List<Work>> getMineWhereStatus(Status status, int page);

    Observable<List<Work>> getWhereSeason(String season, int page);

    Observable<List<Work>> getOrderWatchersCountDesc(int page);
}