package com.konifar.annict.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.konifar.annict.R;
import com.konifar.annict.databinding.ActivityWorkDetailBinding;
import com.konifar.annict.model.Work;
import com.konifar.annict.viewmodel.WorkDetailViewModel;
import javax.inject.Inject;
import org.parceler.Parcels;

public class WorkDetailActivity extends BaseActivity {

  @Inject WorkDetailViewModel viewModel;
  private ActivityWorkDetailBinding binding;

  public static Intent createIntent(Context context, @Nullable Work work) {
    Intent intent = new Intent(context, WorkDetailActivity.class);
    intent.putExtra(Work.TAG, Parcels.wrap(work));
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getComponent().inject(this);

    Work work = Parcels.unwrap(getIntent().getParcelableExtra(Work.TAG));
    viewModel.setWork(work);

    binding = DataBindingUtil.setContentView(this, R.layout.activity_work_detail);
    binding.setViewModel(viewModel);
    initBackToolbar(binding.toolbar);
  }
}
