package com.konifar.annict.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.konifar.annict.BR;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.model.Work;
import com.konifar.annict.util.PageNavigator;
import com.konifar.annict.viewmodel.event.EventBus;
import javax.inject.Inject;
import rx.subscriptions.CompositeSubscription;

public class WorkDetailViewModel extends BaseObservable implements ViewModel {

  private static final String TAG = WorkDetailViewModel.class.getSimpleName();

  private final Context context;
  private final AnnictClient client;
  private final EventBus eventBus;
  private final PageNavigator pageNavigator;
  private final CompositeSubscription compositeSubscription = new CompositeSubscription();

  @Bindable public Work work;

  @Inject public WorkDetailViewModel(Context context, AnnictClient client, EventBus eventBus,
      PageNavigator pageNavigator) {
    this.context = context;
    this.client = client;
    this.eventBus = eventBus;
    this.pageNavigator = pageNavigator;
  }

  public Work getWork() {
    return work;
  }

  public void setWork(Work work) {
    this.work = work;
    notifyPropertyChanged(BR.work);
  }

  @Override public void destroy() {
    compositeSubscription.unsubscribe();
  }
}
