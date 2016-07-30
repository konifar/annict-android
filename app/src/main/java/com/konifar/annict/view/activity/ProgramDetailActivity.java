package com.konifar.annict.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityProgramDetailBinding;
import com.konifar.annict.model.Program;
import com.konifar.annict.viewmodel.ProgramDetailViewModel;

import org.parceler.Parcels;

import javax.inject.Inject;

public class ProgramDetailActivity extends BaseActivity {

    private ActivityProgramDetailBinding binding;

    @Inject
    ProgramDetailViewModel viewModel;

    public static Intent createIntent(Context context, @Nullable Program program) {
        Intent intent = new Intent(context, ProgramDetailActivity.class);
        intent.putExtra(Program.TAG, Parcels.wrap(program));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);

        Program program = Parcels.unwrap(getIntent().getParcelableExtra(Program.TAG));
        viewModel.setProgram(program);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_program_detail);
        binding.setViewModel(viewModel);
        initBackToolbar(binding.toolbar);
    }
}
