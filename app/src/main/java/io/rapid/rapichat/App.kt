package io.rapid.rapichat

import android.app.Application
import io.rapid.rapichat.utils.PrefDelegate

class App : Application(){
    init {
        PrefDelegate.initialize(this)
    }
}
