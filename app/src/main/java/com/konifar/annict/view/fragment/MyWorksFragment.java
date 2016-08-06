package com.konifar.annict.view.fragment;

import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.databinding.FragmentMyWorksBinding;
import com.konifar.annict.databinding.ItemWorkBinding;
import com.konifar.annict.model.Status;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.view.widget.ArrayRecyclerAdapter;
import com.konifar.annict.view.widget.BindingHolder;
import com.konifar.annict.view.widget.InfiniteOnScrollChangeListener;
import com.konifar.annict.view.widget.itemdecoration.DividerItemDecoration;
import com.konifar.annict.viewmodel.MyWorksViewModel;
import com.konifar.annict.viewmodel.WorkItemViewModel;

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

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class MyWorksFragment extends BaseFragment implements TabPage {

    private static final String TAG = MyWorksFragment.class.getSimpleName();

    @Inject
    MyWorksViewModel viewModel;

    @Inject
    CompositeSubscription compositeSubscription;

    private String authCode;

    private FragmentMyWorksBinding binding;

    private MyWorksAdapter adapter;

    public static MyWorksFragment newInstance(@NonNull String authCode, Status status) {
        MyWorksFragment fragment = new MyWorksFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_AUTH_CODE, authCode);
        bundle.putSerializable(Status.class.getSimpleName(), status);
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
            Status status = (Status) getArguments().getSerializable(Status.class.getSimpleName());
            if (status != null) {
                viewModel.setStatus(status);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = FragmentMyWorksBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();
        showWithAuth();

        return binding.getRoot();
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
        adapter = new MyWorksAdapter(getContext());

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

    private void showWithAuth() {
        Subscription sub =
            viewModel.showWithAuth(DefaultPrefs.get(getContext()).getAccessToken(), authCode)
                .subscribe(workViewModels -> {
                    viewModel.recyclerViewVisibility.set(View.VISIBLE);
                    adapter.addAllWithNotify(workViewModels);
                }, throwable -> {
                    viewModel.progressBarVisibility.set(View.GONE);
                    Log.e(TAG, "load auth token error occurred.", throwable);
                });
        compositeSubscription.add(sub);
    }

    private void showNext() {
        Subscription sub = viewModel.showNext().subscribe(workViewModels -> {
            viewModel.incremantePage();
            adapter.addAllWithNotify(workViewModels);
        }, throwable -> {
            viewModel.footerProgressBarVisibility.set(View.GONE);
            Log.e(TAG, "load works error occurred.", throwable);
        });
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

    public class MyWorksAdapter
        extends ArrayRecyclerAdapter<WorkItemViewModel, BindingHolder<ItemWorkBinding>> {

        public MyWorksAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ItemWorkBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.item_work);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ItemWorkBinding> holder, int position) {
            WorkItemViewModel viewModel = getItem(position);
            holder.binding.setViewModel(viewModel);
        }

        public void destroy() {
            Stream.of(list).forEach(WorkItemViewModel::destroy);
        }
    }
}
