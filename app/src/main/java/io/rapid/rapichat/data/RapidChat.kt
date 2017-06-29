package io.rapid.rapichat.data

import io.rapid.rapichat.Config
import io.rapid.Rapid
import io.rapid.RapidCollectionReference


object RapidChat {
    val CHANNELS: RapidCollectionReference<Channel>
        get() = Rapid.getInstance().collection("channels_${Config.RAPID_TODO_COLLECTION_NAME}", Channel::class.java)
    val MESSAGES: RapidCollectionReference<Message>
        get() = Rapid.getInstance().collection("messages_${Config.RAPID_TODO_COLLECTION_NAME}", Message::class.java)
}
