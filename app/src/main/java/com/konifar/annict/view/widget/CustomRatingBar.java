package com.konifar.annict.view.widget;

import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.util.AttributeSet;

@InverseBindingMethods({
    @InverseBindingMethod(type = CustomRatingBar.class, attribute = "rating"),
})
public class CustomRatingBar extends SimpleRatingBar {

    public CustomRatingBar(Context context) {
        super(context);
    }

    public CustomRatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @BindingAdapter("ratingAttrChanged")
    public static void setRatingAttrChanged(CustomRatingBar view, final InverseBindingListener listener) {
        if (listener == null) {
            view.setOnRatingBarChangeListener(null);
        } else {
            view.setOnRatingBarChangeListener((simpleRatingBar, rating, fromUser) -> listener.onChange());
        }
    }
}
