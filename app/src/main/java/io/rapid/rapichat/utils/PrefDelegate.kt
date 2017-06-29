package io.rapid.rapichat.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.reflect.KProperty

/*
 * Android Shared Preferences Delegate for Kotlin
 *
 * Usage:
 *
 * PrefDelegate.init(context)
 * ...
 * var accessToken by stringPref(PREFS_ID, "access_token")
 * var appLaunchCount by intPref(PREFS_ID, "app_launch_count", 0)
 * var autoRefreshEnabled by booleanPref("auto_refresh enabled") // using Default Shared Preferences
 *
 */

abstract class PrefDelegate<T>(val prefName: String?, val prefKey: String) {

    companion object {
        private var context: Context? = null

        /**
         * Initialize PrefDelegate with a Context reference
         * !! This method needs to be called before any other usage of PrefDelegate !!
         */
        fun initialize(context: Context) {
            this.context = context
        }
    }

    protected val prefs: SharedPreferences by lazy {
        if (context != null)
            if (prefName != null) context!!.getSharedPreferences(prefName, Context.MODE_PRIVATE) else PreferenceManager.getDefaultSharedPreferences(context!!)
        else
            throw IllegalStateException("Context was not initialized. Call PrefDelegate.init(context) before using it")
    }

    abstract operator fun getValue(thisRef: Any?, property: KProperty<*>): T
    abstract operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T)
}

fun stringPref(prefKey: String, defaultValue: String? = null) = StringPrefDelegate(null, prefKey, defaultValue)
fun stringPref(prefKey: String, prefName: String? = null, defaultValue: String? = null) = StringPrefDelegate(prefName, prefKey, defaultValue)
class StringPrefDelegate(prefName: String?, prefKey: String, val defaultValue: String?) : PrefDelegate<String?>(prefName, prefKey) {
    override fun getValue(thisRef: Any?, property: KProperty<*>) = prefs.getString(prefKey, defaultValue)
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) = prefs.edit().putString(prefKey, value).apply()
}