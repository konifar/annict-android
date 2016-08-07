package com.konifar.annict.viewmodel;

import com.konifar.annict.BR;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

public class TopSnackbarViewModel extends BaseObservable implements ViewModel {

    @Bindable
    public String text;

    @Bindable
    public String buttonText;

    @Bindable
    public OnClickButtonListener listener;

    public TopSnackbarViewModel(String text, String buttonText, OnClickButtonListener listener) {
        this.text = text;
        this.buttonText = buttonText;
        this.listener = listener;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        notifyPropertyChanged(BR.text);
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
        notifyPropertyChanged(BR.buttonText);
    }

    public OnClickButtonListener getListener() {
        return listener;
    }

    public void setListener(OnClickButtonListener listener) {
        this.listener = listener;
        notifyPropertyChanged(BR.listener);
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    public interface OnClickButtonListener {

        void onClick(View view);
    }
}
