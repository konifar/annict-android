package com.konifar.annict.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityEpisodeDetailBinding;
import com.konifar.annict.model.Program;
import com.konifar.annict.viewmodel.EpisodeDetailViewModel;
import javax.inject.Inject;
import org.parceler.Parcels;

public class EpisodeDetailActivity extends BaseActivity {

  @Inject EpisodeDetailViewModel viewModel;
  private ActivityEpisodeDetailBinding binding;

  public static Intent createIntent(Context context, @Nullable Program program) {
    Intent intent = new Intent(context, EpisodeDetailActivity.class);
    intent.putExtra(Program.TAG, Parcels.wrap(program));
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);

    Program program = Parcels.unwrap(getIntent().getParcelableExtra(Program.TAG));
    viewModel.setProgram(program);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_episode_detail);
    binding.setViewModel(viewModel);
    initBackToolbar(binding.toolbar);
  }
}
