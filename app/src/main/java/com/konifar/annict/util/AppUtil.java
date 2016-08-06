package com.konifar.annict.util;

import com.konifar.annict.BuildConfig;
import com.konifar.annict.R;
import com.konifar.annict.pref.DefaultPrefs;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import javax.inject.Singleton;

@Singleton
public class AppUtil {

    public static String getVersionName(Context context) {
        return "v" + BuildConfig.VERSION_NAME;
    }

    public static void setTaskDescription(Activity activity, String label, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.setTaskDescription(new ActivityManager.TaskDescription(label, null, color));
        }
    }

    public static int getThemeColorPrimary(Context context) {
        TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, value, true);
        return value.data;
    }

    public static boolean hasAccessToken(Context context) {
        return !TextUtils.isEmpty(DefaultPrefs.get(context).getAccessToken());
    }

    public void linkify(Activity activity, TextView textView, String linkText, String url) {
        String text = textView.getText().toString();

        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(text);
        builder.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(View view) {
                                showWebPage(activity, url);
                            }
                        }, text.indexOf(linkText), text.indexOf(linkText) + linkText.length(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void showWebPage(Activity activity, @NonNull String url) {
        CustomTabsIntent intent = new CustomTabsIntent.Builder().setShowTitle(true)
            .setToolbarColor(ContextCompat.getColor(activity, R.color.theme500))
            .build();

        intent.launchUrl(activity, Uri.parse(url));
    }
}
