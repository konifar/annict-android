package com.konifar.annict.repository;

import com.konifar.annict.model.Record;

import rx.Observable;

public interface RecordRepository {

    Observable<Record> create(Long episodeId, String comment, Float rating, boolean shareTwitter, boolean shareFacebook);

    Observable<Record> update(Long recordId, String comment, Float rating, boolean shareTwitter, boolean shareFacebook);
}
