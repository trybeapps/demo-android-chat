package io.rapid.rapichat.channel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.ObservableField
import io.rapid.RapidDocument
import io.rapid.RapidMutateOptions
import io.rapid.Sorting
import io.rapid.rapichat.data.Channel
import io.rapid.rapichat.data.LastMessage
import io.rapid.rapichat.data.Message
import io.rapid.rapichat.data.RapidChat
import io.rapid.rapichat.utils.User
import io.rapid.rapichat.utils.stringPref
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class ChannelViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var channelId: String
    val messages = DiffObservableList<RapidDocument<Message>>(object : DiffObservableList.Callback<RapidDocument<Message>> {
        override fun areContentsTheSame(item1: RapidDocument<Message>, item2: RapidDocument<Message>) = item1.hasSameContentAs(item2)
        override fun areItemsTheSame(item1: RapidDocument<Message>, item2: RapidDocument<Message>) = item1.id == item2.id
    })
    val typingMessage = ObservableField<String>()

    var messagesListener: (() -> Unit)? = null

    fun sendMessage() {
        val newMessage = RapidChat.MESSAGES.newDocument()

        // add new message
        newMessage.mutate(Message(channelId, typingMessage.get(), User.NAME, null), RapidMutateOptions.Builder().fillPropertyWithServerTimestamp("sentDate").build())

        // update last message in channel
        RapidChat.CHANNELS.document(channelId).mutate(Channel(LastMessage(newMessage.id, typingMessage.get())))
        typingMessage.set("")
    }

    fun observe(lifecycleOwner: LifecycleOwner) {

        var lastKnownMessageId by stringPref("last_message_$channelId")
        RapidChat.MESSAGES
                .equalTo("channelId", channelId)
                .orderBy("sentDate", Sorting.ASC)
                .liveData
                .observe(lifecycleOwner, Observer {
                    messages.update(it)
                    if (messages.isNotEmpty())
                        lastKnownMessageId = messages.last().id
                    messagesListener?.invoke()
                })
    }
}