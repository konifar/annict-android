package com.konifar.annict.api.builder;

import android.support.annotation.NonNull;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Programs;
import com.konifar.annict.model.Sort;
import rx.Observable;

public class MeProgramsBuilder implements Builder<Programs> {

  private static final int DEFAULT_PER_PAGE = 30;

  private final AnnictClient client;

  private String fields;

  private String filterIds;

  private String filterChannelIds;

  private String filterWorkIds;

  private String filterStartedAtGt;

  private String filterStartedAtLt;

  private boolean filterUnwatched;

  private boolean filterRebroadcast;

  private int page;

  private int perPage = DEFAULT_PER_PAGE;

  private String sortId;

  private String sortStartedAt;
  
  public MeProgramsBuilder(AnnictClient client, int page) {
    this.client = client;
    this.page = page;
  }

  public MeProgramsBuilder fields(String fields) {
    this.fields = fields;
    return this;
  }

  public MeProgramsBuilder filterIds(String filterIds) {
    this.filterIds = filterIds;
    return this;
  }

  public MeProgramsBuilder filterChannelIds(String filterChannelIds) {
    this.filterChannelIds = filterChannelIds;
    return this;
  }

  public MeProgramsBuilder filterWorkIds(String filterWorkIds) {
    this.filterWorkIds = filterWorkIds;
    return this;
  }

  public MeProgramsBuilder filterStartedAtGt(String filterStartedAtGt) {
    this.filterStartedAtGt = filterStartedAtGt;
    return this;
  }

  public MeProgramsBuilder filterStartedAtLt(String filterStartedAtLt) {
    this.filterStartedAtLt = filterStartedAtLt;
    return this;
  }

  public MeProgramsBuilder filterUnwatched(boolean filterUnWatched) {
    this.filterUnwatched = filterUnWatched;
    return this;
  }

  public MeProgramsBuilder filterRebroadcast(boolean filterRebroadcast) {
    this.filterRebroadcast = filterRebroadcast;
    return this;
  }

  public MeProgramsBuilder page(int page) {
    this.page = page;
    return this;
  }

  public MeProgramsBuilder perPage(int perPage) {
    this.perPage = perPage;
    return this;
  }

  public MeProgramsBuilder sortId(@NonNull Sort sort) {
    this.sortId = sort.toString();
    return this;
  }

  public MeProgramsBuilder sortStartedAt(@NonNull Sort sort) {
    this.sortStartedAt = sort.toString();
    return this;
  }

  public Observable<Programs> build() {
    return client.service.getMeProgarms(fields, filterIds, filterChannelIds, filterWorkIds,
        filterStartedAtGt, filterStartedAtLt, filterUnwatched, filterRebroadcast, page, perPage,
        sortId, sortStartedAt);
  }
}
