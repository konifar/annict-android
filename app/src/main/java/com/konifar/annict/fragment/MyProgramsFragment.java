package com.konifar.annict.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.FragmentMyProgramsBinding;
import com.konifar.annict.prefs.DefaultPrefs;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MyProgramsFragment extends BaseFragment {

    private static final String TAG = MyProgramsFragment.class.getSimpleName();
    private static final String ARG_AUTH_CODE = "auth_code";

    @Inject
    AnnictClient client;

    private String authCode;

    private FragmentMyProgramsBinding binding;

    public static MyProgramsFragment newInstance(@NonNull String authCode) {
        MyProgramsFragment fragment = new MyProgramsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_AUTH_CODE, authCode);
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
        authCode = getArguments().getString(ARG_AUTH_CODE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMyProgramsBinding.inflate(inflater, container, false);

        loadTokenAndPrograms(authCode);

        return binding.getRoot();
    }

    private void loadTokenAndPrograms(@NonNull String authCode) {
        client.postOauthToken(authCode)
                .flatMap(token -> {
                    DefaultPrefs.get(getContext()).putAccessToken(token.accessToken);
                    return client.getMePrograms(1);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(() -> binding.progressBar.setVisibility(View.VISIBLE))
                .doOnCompleted(() -> binding.progressBar.setVisibility(View.GONE))
                .doOnError((throwable) -> binding.progressBar.setVisibility(View.GONE))
                .subscribe(
                        programs -> {
                            Log.d(TAG, "Programs count: " + programs.list.size());
                        },
                        throwable -> {
                            Log.e(TAG, "load auth token error occurred.", throwable);
                        }
                );
    }

}
