package com.konifar.annict.util;

import android.support.v4.text.TextUtilsCompat;
import android.support.v4.view.ViewCompat;
import java.util.Locale;

public class LocaleUtil {

  public static boolean shouldRtl() {
    return TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
        == ViewCompat.LAYOUT_DIRECTION_RTL;
  }
}
