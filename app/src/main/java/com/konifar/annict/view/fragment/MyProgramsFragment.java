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
import com.konifar.annict.databinding.FragmentMyProgramsBinding;
import com.konifar.annict.databinding.ItemProgramBinding;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.view.widget.ArrayRecyclerAdapter;
import com.konifar.annict.view.widget.BindingHolder;
import com.konifar.annict.view.widget.InfiniteOnScrollChangeListener;
import com.konifar.annict.view.widget.itemdecoration.DividerItemDecoration;
import com.konifar.annict.viewmodel.MyProgramItemViewModel;
import com.konifar.annict.viewmodel.MyProgramsViewModel;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MyProgramsFragment extends BaseFragment implements TabPage {

    private static final String TAG = MyProgramsFragment.class.getSimpleName();

    @Inject
    MyProgramsViewModel viewModel;
    @Inject
    CompositeSubscription compositeSubscription;

    private String authCode;

    private FragmentMyProgramsBinding binding;

    private MyProgramsAdapter adapter;

    public static MyProgramsFragment newInstance(@NonNull String authCode) {
        MyProgramsFragment fragment = new MyProgramsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_AUTH_CODE, authCode);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MyProgramsFragment newInstance() {
        return new MyProgramsFragment();
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
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyProgramsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();
        showWithAuth();

        return binding.getRoot();
    }

    private void showWithAuth() {
        Subscription sub = viewModel.showWithAuth(DefaultPrefs.get(getContext()).getAccessToken(), authCode)
                .subscribe(
                        viewModels -> {
                            viewModel.recyclerViewVisibility.set(View.VISIBLE);
                            adapter.addAllWithNotify(viewModels);
                        },
                        throwable -> {
                            viewModel.progressBarVisibility.set(View.GONE);
                            Log.e(TAG, "load auth token error occurred.", throwable);
                        }
                );
        compositeSubscription.add(sub);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeSubscription.unsubscribe();
        adapter.destroy();
        viewModel.destroy();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new MyProgramsAdapter(getContext());

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(linearLayoutManager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));

        binding.nestedScrollView.setOnScrollChangeListener(
                new InfiniteOnScrollChangeListener(binding.recyclerView, linearLayoutManager) {
                    @Override
                    public void onLoadMore() {
                        showNext();
                    }
                });
    }

    private void showNext() {
        Subscription sub = viewModel.showNext().subscribe(
                workViewModels -> {
                    viewModel.incremantePage();
                    adapter.addAllWithNotify(workViewModels);
                },
                throwable -> {
                    viewModel.footerProgressBarVisibility.set(View.GONE);
                    Log.e(TAG, "load works error occurred.", throwable);
                }
        );
        compositeSubscription.add(sub);
    }

    @Override
    public void scrollToTop() {
        binding.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    protected class MyProgramsAdapter extends ArrayRecyclerAdapter<MyProgramItemViewModel, BindingHolder<ItemProgramBinding>> {

        public MyProgramsAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ItemProgramBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.item_program);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ItemProgramBinding> holder, int position) {
            MyProgramItemViewModel viewModel = getItem(position);
            holder.binding.setViewModel(viewModel);
        }

        public void destroy() {
            Stream.of(list).forEach(MyProgramItemViewModel::destroy);
        }
    }

}
