package com.konifar.annict.viewmodel;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.R;
import com.konifar.annict.util.PageNavigator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainViewModelTest {

    private MainViewModel mainViewModel;
    @Mock
    private PageNavigator navigator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainViewModel = new MainViewModel(navigator);
    }

    @Test
    public void testShowData() {
        final int layoutResId = R.id.content_view;

        // When accessToken is not empty
        mainViewModel.showData("abcdefghijklmn", "", layoutResId);
        verify(navigator, times(1)).replaceMyProgramsFragment(layoutResId);

        // When accessToken is empty and authCode is not empty
        mainViewModel.showData("", "abcdefghijklmn", layoutResId);
        verify(navigator, times(1)).replaceMyProgramsFragment("abcdefghijklmn", layoutResId);

        // When accessToken and authCode is empty
        mainViewModel.showData("", "", layoutResId);
        verify(navigator, times(1)).startLoginActivity();
    }
}
