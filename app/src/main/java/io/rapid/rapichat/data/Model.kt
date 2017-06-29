package io.rapid.rapichat.data

data class Message(
        val channelId: String,
        val text: String,
        val senderName: String,
        val sentDate: Long?
)

data class LastMessage(
        val id: String,
        val text: String
)

data class Channel(
        val lastMessage: LastMessage
)