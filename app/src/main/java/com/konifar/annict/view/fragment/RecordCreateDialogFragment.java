package com.konifar.annict.view.fragment;

import com.konifar.annict.R;
import com.konifar.annict.databinding.FragmentRecordCreateDialogBinding;
import com.konifar.annict.di.FragmentComponent;
import com.konifar.annict.di.FragmentModule;
import com.konifar.annict.model.Program;
import com.konifar.annict.view.activity.BaseActivity;
import com.konifar.annict.viewmodel.RecordCreateViewModel;

import org.parceler.Parcels;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

public class RecordCreateDialogFragment extends DialogFragment {

    public static final String TAG = RecordCreateDialogFragment.class.getSimpleName();

    @Inject
    RecordCreateViewModel viewModel;

    private FragmentRecordCreateDialogBinding binding;

    private FragmentComponent fragmentComponent;

    public static RecordCreateDialogFragment newInstance(@NonNull Program program) {
        RecordCreateDialogFragment fragment = new RecordCreateDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Program.TAG, Parcels.wrap(program));
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
            Program program = Parcels.unwrap(getArguments().getParcelable(Program.TAG));
            viewModel.bindProgram(program);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow()
            .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
            R.layout.fragment_record_create_dialog, null, false);
        binding.setViewModel(viewModel);

        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogWindowAnimation;

        return dialog;
    }

    @NonNull
    public FragmentComponent getComponent() {
        if (fragmentComponent != null) {
            return fragmentComponent;
        }

        Activity activity = getActivity();
        if (activity instanceof BaseActivity) {
            fragmentComponent = ((BaseActivity) activity).getComponent().plus(new FragmentModule(this));
            return fragmentComponent;
        } else {
            throw new IllegalStateException(
                "The activity of this fragment is not an instance of BaseActivity");
        }
    }
}
