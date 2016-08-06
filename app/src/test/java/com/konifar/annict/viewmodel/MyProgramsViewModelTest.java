package com.konifar.annict.viewmodel;

import android.content.Context;
import android.view.View;
import com.konifar.annict.BuildConfig;
import com.konifar.annict.helper.MockHelper;
import com.konifar.annict.helper.RxHelper;
import com.konifar.annict.model.Program;
import com.konifar.annict.repository.ProgramRepositoryImpl;
import com.konifar.annict.util.PageNavigator;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import rx.Observable;
import rx.android.plugins.RxAndroidPlugins;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class) @Config(constants = BuildConfig.class, sdk = 21)
public class MyProgramsViewModelTest {

  private MyProgramsViewModel viewModel;

  @Mock private Context context;
  @Mock private ProgramRepositoryImpl repository;
  @Mock private PageNavigator pageNavigator;

  @Before public void setUp() {
    MockitoAnnotations.initMocks(this);
    RxHelper.setUpRx();

    viewModel = new MyProgramsViewModel(context, repository, pageNavigator);
  }

  @After public void tearDown() {
    RxAndroidPlugins.getInstance().reset();
  }

  @Test public void testShowWithAuthWhenSuccess() {
    Observable<List<Program>> mockPrograms = Observable.just(MockHelper.mockPrograms(5));
    when(repository.getMineOrderByStartedAtDesc(1)).thenReturn(mockPrograms);

    viewModel.showWithAuth("accesstoken", "authcode").subscribe();
    verify(repository, times(1)).getMineOrderByStartedAtDesc(1);
    assertEquals(View.GONE, viewModel.progressBarVisibility.get());
  }

  @Test public void testShowWithAuthWhenFailure() {
    Observable<List<Program>> mockPrograms = Observable.just(new ArrayList<>());
    when(repository.getMineOrderByStartedAtDesc(1)).thenReturn(mockPrograms);

    viewModel.showWithAuth("accesstoken", "authcode").subscribe();
    verify(repository, times(1)).getMineOrderByStartedAtDesc(1);
    assertEquals(View.GONE, viewModel.progressBarVisibility.get());
  }

  @Test public void testShowWithAuthWhenEmpty() {
    Observable<List<Program>> mockPrograms = Observable.just(new ArrayList<>());
    when(repository.getMineOrderByStartedAtDesc(1)).thenReturn(mockPrograms);

    viewModel.showWithAuth("accesstoken", "authcode").subscribe();
    verify(repository, times(1)).getMineOrderByStartedAtDesc(1);
    assertEquals(View.GONE, viewModel.progressBarVisibility.get());
  }
}
