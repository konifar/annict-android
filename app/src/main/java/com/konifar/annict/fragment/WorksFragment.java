package com.konifar.annict.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konifar.annict.databinding.FragmentWorksBinding;


public class WorksFragment extends BaseFragment {

    private static final String TAG = WorksFragment.class.getSimpleName();

    private FragmentWorksBinding binding;

    public static WorksFragment newInstance() {
        WorksFragment fragment = new WorksFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
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
        binding = FragmentWorksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

}
