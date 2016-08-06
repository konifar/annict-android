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

    @BindingAdapter({"photoImageUrl", "photoImageSize"})
    public static void setPhotoImageUrlWithSize(ImageView imageView, @Nullable String imageUrl,
        float sizeInDimen) {
        setImageUrlWithSize(imageView, imageUrl, sizeInDimen, R.color.grey200);
    }

    public static void setImageUrlWithSize(ImageView imageView, @Nullable String imageUrl,
        float sizeInDimen, @DrawableRes int placeholderResId) {
        if (TextUtils.isEmpty(imageUrl)) {
            imageView.setImageDrawable(
                ContextCompat.getDrawable(imageView.getContext(), placeholderResId));
        } else {
            final int size = Math.round(sizeInDimen);
            Picasso.with(imageView.getContext())
                .load(imageUrl)
                .resize(size, size)
                .centerInside()
                .placeholder(placeholderResId)
                .error(placeholderResId)
                .into(imageView);
        }
    }
}
