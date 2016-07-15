package com.konifar.annict.viewmodel;

import android.content.Context;
import android.view.View;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.api.AnnictClient;
import com.konifar.annict.helper.MockHelper;
import com.konifar.annict.helper.RxHelper;
import com.konifar.annict.model.Programs;
import com.konifar.annict.viewmodel.event.EventBus;
import com.konifar.annict.viewmodel.event.MyProgramsLoadedEvent;

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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MyProgramsViewModelTest {

    private MyProgramsViewModel viewModel;

    @Mock
    private Context context;
    @Mock
    private AnnictClient annictClient;
    @Mock
    private EventBus eventBus;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        RxHelper.setUpRx();

        viewModel = new MyProgramsViewModel(context, annictClient, eventBus);
    }

    @After
    public void tearDown() {
        RxAndroidPlugins.getInstance().reset();
    }

    @Test
    public void testShowProgramsWhenSuccess() {
        Programs programs = MockHelper.mockPrograms(5);
        when(annictClient.getMePrograms(1)).thenReturn(Observable.just(programs));

        viewModel.showPrograms("abcdefghij", "abcdefghij");
        verify(eventBus, times(1)).post(any(MyProgramsLoadedEvent.class));
        assertEquals(View.GONE, viewModel.progressBarVisibility.get());
        assertEquals(View.VISIBLE, viewModel.recyclerViewVisibility.get());
    }

    @Test
    public void testShowProgramsWhenFailure() {
        when(annictClient.getMePrograms(1)).thenReturn(Observable.just(new Programs()));

        viewModel.showPrograms("abcdefghij", "abcdefghij");
        verify(eventBus, never()).post(any(MyProgramsLoadedEvent.class));
        assertEquals(View.GONE, viewModel.progressBarVisibility.get());
        assertEquals(View.GONE, viewModel.recyclerViewVisibility.get());
    }

    @Test
    public void testShowProgramsWhenEmpty() {
        Programs programs = MockHelper.mockPrograms(0);
        when(annictClient.getMePrograms(1)).thenReturn(Observable.just(programs));

        viewModel.showPrograms("abcdefghij", "abcdefghij");
        verify(eventBus, never()).post(any(MyProgramsLoadedEvent.class));
        assertEquals(View.GONE, viewModel.progressBarVisibility.get());
    }

}
