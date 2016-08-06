package com.konifar.annict.api.builder;

import android.support.annotation.NonNull;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Sort;
import com.konifar.annict.model.Works;
import rx.Observable;

public class WorksBuilder implements Builder<Works> {

  private static final int DEFAULT_PER_PAGE = 30;

  private final AnnictClient client;

  private String fields;

  private String filterIds;

  private String filterSeason;

  private String filterTitle;

  private int page;

  private int perPage = DEFAULT_PER_PAGE;

  private String sortId;

  private String sortReason;

  private String sortWatchersCount;

  public WorksBuilder(AnnictClient client, int page) {
    this.client = client;
    this.page = page;
  }

  public WorksBuilder fields(String fields) {
    this.fields = fields;
    return this;
  }

  public WorksBuilder filterIds(String filterIds) {
    this.filterIds = filterIds;
    return this;
  }

  public WorksBuilder filterSeason(String filterSeason) {
    this.filterSeason = filterSeason;
    return this;
  }

  public WorksBuilder filterTitle(String filterTitle) {
    this.filterTitle = filterTitle;
    return this;
  }

  public WorksBuilder page(int page) {
    this.page = page;
    return this;
  }

  public WorksBuilder perPage(int perPage) {
    this.perPage = perPage;
    return this;
  }

  public WorksBuilder sortId(@NonNull Sort sort) {
    this.sortId = sort.toString();
    return this;
  }

  public WorksBuilder sortReason(@NonNull Sort sort) {
    this.sortReason = sort.toString();
    return this;
  }

  public WorksBuilder sortWatchersCount(@NonNull Sort sort) {
    this.sortWatchersCount = sort.toString();
    return this;
  }

  public Observable<Works> build() {
    return client.service.getWorks(fields, filterIds, filterSeason, filterTitle, page, perPage,
        sortId, sortReason, sortWatchersCount);
  }
}
