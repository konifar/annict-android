package com.konifar.annict.viewmodel;

import android.content.Context;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.viewmodel.event.EventBus;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

public class SettingsViewModel implements ViewModel {

  private static final String TAG = RecordCreateViewModel.class.getSimpleName();

  private final Context context;
  private final AnnictClient client;
  private final EventBus eventBus;
  private final PageNavigator pageNavigator;
  private final CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Inject public SettingsViewModel(Context context, AnnictClient client, EventBus eventBus,
      PageNavigator pageNavigator) {
    this.context = context;
    this.client = client;
    this.eventBus = eventBus;
    this.pageNavigator = pageNavigator;
  }

  @Override public void destroy() {
    compositeSubscription.unsubscribe();
  }
}
