package com.konifar.annict.view.widget;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class InfiniteOnScrollChangeListener
    implements NestedScrollView.OnScrollChangeListener {

  private int previousTotalItemCount = 0;

  private boolean loading = true;

  private RecyclerView recyclerView;

  private LinearLayoutManager linearLayoutManager;

  public InfiniteOnScrollChangeListener(RecyclerView recyclerView,
      LinearLayoutManager linearLayoutManager) {
    this.recyclerView = recyclerView;
    this.linearLayoutManager = linearLayoutManager;
  }

  @Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
      int oldScrollY) {
    int visibleItemCount = recyclerView.getChildCount();
    int totalItemCount = linearLayoutManager.getItemCount();
    int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

    if (loading) {
      if (totalItemCount > previousTotalItemCount) {
        loading = false;
        previousTotalItemCount = totalItemCount;
      }
    }

    if (!loading && (totalItemCount - visibleItemCount) <= lastVisibleItem) {
      onLoadMore();
      loading = true;
    }
  }

  public abstract void onLoadMore();
}
