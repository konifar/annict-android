package com.konifar.annict.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityMainBinding;
import com.konifar.annict.fragment.LoginFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getComponent().inject(this);
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        replaceFragment(LoginFragment.newInstance(), R.id.content_view);
    }

}
