package com.konifar.annict.viewmodel;

import android.content.Context;
import android.support.annotation.NonNull;
import com.konifar.annict.model.Status;
import com.konifar.annict.model.Work;
import com.konifar.annict.repository.StatusRepository;
import com.konifar.annict.repository.WorkRepository;
import com.konifar.annict.util.PageNavigator;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

public class MyWorksViewModel extends AbstractListViewModel<Work, WorkItemViewModel> {

  private final Context context;
  private final WorkRepository workRepository;
  private final StatusRepository statusRepository;
  private final PageNavigator pageNavigator;

  private Status status = Status.NO_SELECT;

  @Inject public MyWorksViewModel(Context context, WorkRepository workRepository,
      StatusRepository statusRepository, PageNavigator pageNavigator) {
    this.context = context;
    this.workRepository = workRepository;
    this.statusRepository = statusRepository;
    this.pageNavigator = pageNavigator;
  }

  public void setStatus(@NonNull Status status) {
    this.status = status;
  }

  @Override public Observable<List<Work>> getLoadObservable(int page) {
    return workRepository.getMineWhereStatus(status, page);
  }

  @Override public Observable<List<Work>> getLoadObservableWithAuth(String authCode, int page) {
    return workRepository.getMineWhereStatusWithAuth(authCode, status, page);
  }

  @Override WorkItemViewModel convertToViewModel(Work work) {
    return new WorkItemViewModel(context, work, status, pageNavigator, statusRepository);
  }
}
