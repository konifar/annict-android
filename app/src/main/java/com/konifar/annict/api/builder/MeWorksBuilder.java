package com.konifar.annict.api.builder;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Sort;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Works;

import android.support.annotation.NonNull;

import rx.Observable;

public class MeWorksBuilder implements Builder<Works> {

    private static final int DEFAULT_PER_PAGE = 30;

    private final AnnictClient client;

    private String fields;

    private String filterIds;

    private String filterSeason;

    private String filterTitle;

    private String filterStatus;

    private int page;

    private int perPage = DEFAULT_PER_PAGE;

    private String sortId;

    private String sortReason;

    private String sortWatchersCount;

    public MeWorksBuilder(AnnictClient client, int page) {
        this.client = client;
        this.page = page;
    }

    public MeWorksBuilder fields(String fields) {
        this.fields = fields;
        return this;
    }

    public MeWorksBuilder filterIds(String filterIds) {
        this.filterIds = filterIds;
        return this;
    }

    public MeWorksBuilder filterSeason(String filterSeason) {
        this.filterSeason = filterSeason;
        return this;
    }

    public MeWorksBuilder filterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
        return this;
    }

    public MeWorksBuilder filterStatus(@NonNull Status filterStatus) {
        this.filterStatus = filterStatus.toString();
        return this;
    }

    public MeWorksBuilder page(int page) {
        this.page = page;
        return this;
    }

    public MeWorksBuilder perPage(int perPage) {
        this.perPage = perPage;
        return this;
    }

    public MeWorksBuilder sortId(@NonNull Sort sort) {
        this.sortId = sort.toString();
        return this;
    }

    public MeWorksBuilder sortReason(@NonNull Sort sort) {
        this.sortReason = sort.toString();
        return this;
    }

    public MeWorksBuilder sortWatchersCount(@NonNull Sort sort) {
        this.sortWatchersCount = sort.toString();
        return this;
    }

    public Observable<Works> build() {
        return client.service.getMeWorks(fields, filterIds, filterSeason, filterTitle, filterStatus,
            page, perPage, sortId, sortReason, sortWatchersCount);
    }
}
