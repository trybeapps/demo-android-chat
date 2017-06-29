package io.rapid.rapichat.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat

fun Context.getCompatColor(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)
fun Context.getCompatDrawable(@DrawableRes drawableRes: Int) = ContextCompat.getDrawable(this, drawableRes)
fun Drawable.setCompatTint(@ColorInt color: Int) = DrawableCompat.setTint(this, color)

