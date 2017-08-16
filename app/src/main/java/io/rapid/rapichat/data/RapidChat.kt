package io.rapid.rapichat.data

import io.rapid.Rapid
import io.rapid.RapidCollectionReference


object RapidChat {
    val CHANNELS: RapidCollectionReference<Channel>
        get() = Rapid.getInstance().collection("channels", Channel::class.java)
    val MESSAGES: RapidCollectionReference<Message>
        get() = Rapid.getInstance().collection("messages", Message::class.java)
}
