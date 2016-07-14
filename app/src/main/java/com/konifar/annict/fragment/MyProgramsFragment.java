package com.konifar.annict.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.konifar.annict.R;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.FragmentMyProgramsBinding;
import com.konifar.annict.databinding.ItemProgramBinding;
import com.konifar.annict.model.Programs;
import com.konifar.annict.prefs.DefaultPrefs;
import com.konifar.annict.util.AppUtil;
import com.konifar.annict.viewmodels.ProgramViewModel;
import com.konifar.annict.widget.ArrayRecyclerAdapter;
import com.konifar.annict.widget.BindingHolder;
import com.konifar.annict.widget.itemdecoration.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MyProgramsFragment extends BaseFragment {

    private static final String TAG = MyProgramsFragment.class.getSimpleName();
    private static final String ARG_AUTH_CODE = "auth_code";

    @Inject
    AnnictClient client;

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

        initRecyclerView();
        loadTokenAndPrograms();

        return binding.getRoot();
    }

    private void initRecyclerView() {
        adapter = new MyProgramsAdapter(getContext());

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    private void loadTokenAndPrograms() {
        getLoadObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> binding.progressBar.setVisibility(View.VISIBLE))
                .doOnCompleted(() -> binding.progressBar.setVisibility(View.GONE))
                .doOnError((throwable) -> binding.progressBar.setVisibility(View.GONE))
                .subscribe(this::addAll,
                        throwable -> Log.e(TAG, "load auth token error occurred.", throwable)
                );
    }

    private Observable<Programs> getLoadObservable() {
        if (AppUtil.hasAccessToken(getContext())) {
            return client.getMePrograms(1);
        } else {
            return client.postOauthToken(authCode)
                    .flatMap(token -> {
                        DefaultPrefs.get(getContext()).putAccessToken(token.accessToken);
                        return client.getMePrograms(1);
                    });
        }
    }

    private void addAll(Programs programs) {
        List<ProgramViewModel> models = Stream.of(programs.list)
                .map(ProgramViewModel::new)
                .collect(Collectors.toList());
        binding.recyclerView.setVisibility(View.VISIBLE);
        adapter.addAll(models);
        Log.d(TAG, "Programs count: " + programs.list.size());
    }

    protected class MyProgramsAdapter extends ArrayRecyclerAdapter<ProgramViewModel, BindingHolder<ItemProgramBinding>> {

        public MyProgramsAdapter(@NonNull Context context) {
            super(context);
        }

        @Override
        public BindingHolder<ItemProgramBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BindingHolder<>(getContext(), parent, R.layout.item_program);
        }

        @Override
        public void onBindViewHolder(BindingHolder<ItemProgramBinding> holder, int position) {
            ProgramViewModel programViewModel = getItem(position);
            ItemProgramBinding binding = holder.binding;
            binding.setProgramViewModel(programViewModel);
        }
    }

}
