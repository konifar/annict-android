package com.konifar.annict.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.FragmentLoginBinding;
import com.konifar.annict.util.AppUtil;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    @Inject
    AnnictClient client;
    @Inject
    CompositeSubscription compositeSubscription;

    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Subscription sub = client.authorize()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        jsonObject -> {
                            Log.d(TAG, jsonObject.toString() + "");
                        },
                        throwable -> Log.e(TAG, "Failed to authorize.", throwable)
                );

        compositeSubscription.add(sub);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        binding.btnLogin.setOnClickListener(v -> {
            Uri uri = Uri.parse("https://annict.com/oauth/authorize")
                    .buildUpon()
                    .appendQueryParameter("client_id", BuildConfig.ANNICT_APPLICATION_ID)
                    .appendQueryParameter("response_type", "code")
                    .appendQueryParameter("redirect_uri", "intent://annict-android/authorize")
                    .build();

            AppUtil.showWebPage(getActivity(), uri.toString());
        });
    }

}
