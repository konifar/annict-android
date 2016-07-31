package com.konifar.annict.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.R;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.FragmentMyProgramsBinding;
import com.konifar.annict.databinding.ItemProgramBinding;
import com.konifar.annict.pref.DefaultPrefs;
import com.konifar.annict.view.widget.ArrayRecyclerAdapter;
import com.konifar.annict.view.widget.BindingHolder;
import com.konifar.annict.view.widget.InfiniteOnScrollChangeListener;
import com.konifar.annict.view.widget.itemdecoration.DividerItemDecoration;
import com.konifar.annict.viewmodel.MyProgramItemViewModel;
import com.konifar.annict.viewmodel.MyProgramsViewModel;
import com.konifar.annict.viewmodel.event.EventBus;
import com.konifar.annict.viewmodel.event.MyProgramsLoadedEvent;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class MyProgramsFragment extends BaseFragment implements MainTabPage {

    private static final String ARG_AUTH_CODE = "auth_code";

    @Inject
    AnnictClient client;
    @Inject
    MyProgramsViewModel viewModel;
    @Inject
    EventBus eventBus;
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

    @Override
    public void onResume() {
        super.onResume();
        Subscription sub = eventBus.observe(MyProgramsLoadedEvent.class)
                .subscribe(event -> adapter.addAllWithNotify(event.myProgramItemViewModels));
        compositeSubscription.add(sub);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyProgramsBinding.inflate(inflater, container, false);
        binding.setViewModel(viewModel);

        initRecyclerView();
        viewModel.showPrograms(DefaultPrefs.get(getContext()).getAccessToken(), authCode);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeSubscription.unsubscribe();
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
                        viewModel.showNextPrograms();
                    }
                });
    }

    @Override
    public int getTitleResId() {
        return R.string.my_programs_title;
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
    }

}
