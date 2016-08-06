package com.konifar.annict.util;

import com.konifar.annict.R;
import com.squareup.picasso.Picasso;

import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

public class DataBindingHelper {

    @BindingAdapter("photoImageUrl")
    public static void setPhotoImageUrlWithSize(ImageView imageView, @Nullable String imageUrl) {
        setImageUrlWithSize(imageView, imageUrl, R.color.grey200);
    }

    private static void setImageUrlWithSize(ImageView imageView, @Nullable String imageUrl,
        @DrawableRes int placeholderResId) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            Picasso.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(placeholderResId)
                .error(placeholderResId)
                .into(imageView);
        }
    }
}
