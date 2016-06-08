package com.konifar.annict.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.databinding.FragmentLoginBinding;
import com.konifar.annict.util.AppUtil;

import javax.inject.Inject;


public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getSimpleName();

    @Inject
    AnnictClient client;

    private FragmentLoginBinding binding;

    public static LoginFragment newInstance() {
        return new LoginFragment();
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
        binding.btnLogin.setOnClickListener(v ->
                AppUtil.showWebPage(getActivity(), client.getOAuthUrl()));
    }

}
