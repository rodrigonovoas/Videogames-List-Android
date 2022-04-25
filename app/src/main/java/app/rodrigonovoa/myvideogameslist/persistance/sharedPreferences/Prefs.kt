package app.rodrigonovoa.myvideogameslist.persistance.sharedPreferences

import android.content.Context
import android.content.SharedPreferences

class Prefs(val context: Context) {
    private val SHARED_PREFS_CODE = "SHARED_PREFS_CODE"
    private val SHARED_PREFERENCES_USERNAME = "SHARED_PREFERENCES_USERNAME"
    private val SHARED_PREFERENCES_SKIP_SPLASH = "SHARED_PREFERENCES_SKIP_SPLASH"


    private val preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS_CODE, Context.MODE_PRIVATE)

    var username: String
        get() = preferences.getString(SHARED_PREFERENCES_USERNAME, "") ?: ""
        set(value) = preferences.edit().putString(SHARED_PREFERENCES_USERNAME, value).apply()

    var skip_splash: Boolean
        get() = preferences.getBoolean(SHARED_PREFERENCES_SKIP_SPLASH, false)
        set(value) = preferences.edit().putBoolean(SHARED_PREFERENCES_SKIP_SPLASH, value).apply()
}