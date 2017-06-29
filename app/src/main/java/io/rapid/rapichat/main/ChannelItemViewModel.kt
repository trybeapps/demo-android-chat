package io.rapid.rapichat.main

import android.databinding.BaseObservable
import android.databinding.Bindable
import io.rapid.RapidDocument
import io.rapid.rapichat.BR
import io.rapid.rapichat.data.Channel
import io.rapid.rapichat.utils.stringPref

class ChannelItemViewModel(val doc: RapidDocument<Channel>) : BaseObservable() {

    val channel = doc.body

    @Bindable
    fun isUnread(): Boolean {
        val lastKnownMessageId by stringPref("last_message_${doc.id}")
        return channel.lastMessage.id != lastKnownMessageId
    }

    fun refreshUnread() {
        notifyPropertyChanged(BR.unread)
    }
}
