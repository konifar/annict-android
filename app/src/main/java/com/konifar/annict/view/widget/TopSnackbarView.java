package com.konifar.annict.view.widget;

import com.konifar.annict.R;
import com.konifar.annict.databinding.ViewTopSnackbarBinding;
import com.konifar.annict.viewmodel.TopSnackbarViewModel;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

public class TopSnackbarView extends FrameLayout {

    private ViewTopSnackbarBinding binding;

    private Animation inAnimation;

    private Animation outAnimation;

    public TopSnackbarView(Context context) {
        this(context, null);
    }

    public TopSnackbarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopSnackbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.view_top_snackbar, this, true);
        initAnimations();
    }

    public void setViewModel(TopSnackbarViewModel viewModel) {
        binding.setViewModel(viewModel);
    }

    private void initAnimations() {
        inAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.view_top_snackbar_in);
        outAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.view_top_snackbar_out);
    }

    public void show() {
        if (!isVisible()) {
            show(true);
        }
    }

    public void show(long visibleMills) {
        if (!isVisible()) {
            show(true);
            new Handler().postDelayed(this::hide, visibleMills);
        }
    }

    public void show(boolean withAnimation) {
        if (withAnimation) {
            startAnimation(inAnimation);
        }
        setVisibility(View.VISIBLE);
    }

    public void hide() {
        if (isVisible()) {
            hide(true);
        }
    }

    public void hide(boolean withAnimation) {
        if (withAnimation) {
            startAnimation(outAnimation);
        }
        setVisibility(View.GONE);
    }

    public boolean isVisible() {
        return this.getVisibility() == View.VISIBLE;
    }
}
