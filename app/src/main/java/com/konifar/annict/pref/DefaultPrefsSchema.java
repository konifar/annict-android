package com.konifar.annict.pref;

import com.rejasupotaro.android.kvs.annotations.Key;
import com.rejasupotaro.android.kvs.annotations.Table;

@Table(name = "com.konifar.annict_preferences") public interface DefaultPrefsSchema {

  @Key(name = "access_token") String accessToken = "";
}
