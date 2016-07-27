package com.konifar.annict.viewmodel;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.api.AnnictClient;
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
public class LoginViewModelTest {

    private LoginViewModel loginViewModel;
    @Mock
    private PageNavigator navigator;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        loginViewModel = new LoginViewModel(navigator);
    }

    @Test
    public void testOnClickLoginButton() {
        loginViewModel.onClickLoginButton(null);
        verify(navigator, times(1)).startCustomTab(AnnictClient.getOAuthUrl());
    }
}
