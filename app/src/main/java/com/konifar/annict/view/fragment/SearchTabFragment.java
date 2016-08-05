package com.konifar.annict.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.databinding.FragmentSearchTabBinding;
import com.konifar.annict.databinding.ItemSearchBinding;
import com.konifar.annict.model.SearchType;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.view.widget.ArrayRecyclerAdapter;
import com.konifar.annict.view.widget.BindingHolder;
import com.konifar.annict.view.widget.InfiniteOnScrollChangeListener;
import com.konifar.annict.view.widget.itemdecoration.DividerItemDecoration;
import com.konifar.annict.viewmodel.SearchItemViewModel;
import com.konifar.annict.viewmodel.SearchTabViewModel;

import javax.inject.Inject;


public class SearchTabFragment extends BaseFragment implements MainTabPage {

    private static final String TAG = SearchTabFragment.class.getSimpleName();

    @Inject
    SearchTabViewModel viewModel;

    private String authCode;

    private FragmentSearchTabBinding binding;

    private SearchItemsAdapter adapter;

    public static SearchTabFragment newInstance(@NonNull String authCode, SearchType type) {
        SearchTabFragment fragment = new SearchTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_AUTH_CODE, authCode);
        bundle.putSerializable(SearchType.class.getSimpleName(), type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authCode = getArguments().getString(ARG_AUTH_CODE);
            SearchType type = (SearchType) getArguments().getSerializable(SearchType.class.getSimpleName());
            if (type != null) viewModel.setType(type);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSearchTabBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();
        viewModel.showWorks(DefaultPrefs.get(getContext()).getAccessToken(), authCode)
                .subscribe(
                        workViewModels -> {
                            viewModel.recyclerViewVisibility.set(View.VISIBLE);
                            adapter.addAllWithNotify(workViewModels);
                        },
                        throwable -> {
                            viewModel.progressBarVisibility.set(View.GONE);
                            Log.e(TAG, "load auth token error occurred.", throwable);
                        }
                );
        ;

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.destroy();
        viewModel.destroy();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new SearchItemsAdapter(getContext());

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));

        binding.nestedScrollView.setOnScrollChangeListener(
                new InfiniteOnScrollChangeListener(binding.recyclerView, linearLayoutManager) {
                    @Override
                    public void onLoadMore() {
                        viewModel.showNextWorks()
                                .subscribe(
                                        workViewModels -> {
                                            viewModel.incremantePage();
                                            adapter.addAllWithNotify(workViewModels);
                                        },
                                        throwable -> {
                                            viewModel.footerProgressBarVisibility.set(View.GONE);
                                            Log.e(TAG, "load works error occurred.", throwable);
                                        }
                                );
                    }
                });
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    public class SearchItemsAdapter extends ArrayRecyclerAdapter<SearchItemViewModel, BindingHolder<ItemSearchBinding>> {

        public SearchItemsAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ItemSearchBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.item_search);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ItemSearchBinding> holder, int position) {
            SearchItemViewModel viewModel = getItem(position);
            holder.binding.setViewModel(viewModel);
        }

        public void destroy() {
            Stream.of(list).forEach(SearchItemViewModel::destroy);
        }
    }

}
