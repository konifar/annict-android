package com.konifar.annict.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.konifar.annict.R;

public abstract class LoadingFooterArrayRecyclerAdapter<T, VH extends RecyclerView.ViewHolder>
        extends HeaderFooterArrayRecyclerAdapter<T, VH> {

    private int loadingFooterItemCount = 0;

    public LoadingFooterArrayRecyclerAdapter(@NonNull Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderItemViewHolder(ViewGroup parent, int headerViewType) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterItemViewHolder(ViewGroup parent, int footerViewType) {
        return new BindingHolder<>(context, parent, R.layout.view_loading);
    }

    @Override
    protected int getHeaderItemCount() {
        return 0;
    }

    @Override
    protected int getFooterItemCount() {
        return loadingFooterItemCount;
    }

    @Override
    protected void onBindHeaderItemViewHolder(RecyclerView.ViewHolder headerViewHolder, int position) {
        // Do nothing
    }

    @Override
    protected void onBindFooterItemViewHolder(RecyclerView.ViewHolder footerViewHolder, int position) {
        // Do nothing
    }

    public void toggleFooterVisibility() {
        if (loadingFooterItemCount == 0) {
            showFooterVisibility();
        } else {
            hideFooterVisibility();
        }
    }

    public void showFooterVisibility() {
        loadingFooterItemCount = 1;
        notifyFooterItemInserted(1);
    }

    public void hideFooterVisibility() {
        loadingFooterItemCount = 0;
    }
}
