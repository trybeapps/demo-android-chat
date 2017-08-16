package io.rapid.rapichat.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.databinding.ObservableField
import io.rapid.lifecycle.RapidLiveData
import io.rapid.rapichat.data.Channel
import io.rapid.rapichat.data.LastMessage
import io.rapid.rapichat.data.RapidChat
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val channels = DiffObservableList<ChannelItemViewModel>(object : DiffObservableList.Callback<ChannelItemViewModel> {
        override fun areContentsTheSame(item1: ChannelItemViewModel, item2: ChannelItemViewModel) = item1.doc.hasSameContentAs(item2.doc)
        override fun areItemsTheSame(item1: ChannelItemViewModel, item2: ChannelItemViewModel) = item1.doc.id == item2.doc.id
    })

    val newChannelName = ObservableField<String>()


    fun observe(lifecycleOwner: LifecycleOwner) {
        RapidLiveData.from(RapidChat.CHANNELS).observe(lifecycleOwner, Observer {
            channels.update(it!!.map { ChannelItemViewModel(it) })
        })
    }


    fun refreshUnread() {
        channels.forEach { it.refreshUnread() }
    }


    fun addChannel() {
        RapidChat.CHANNELS.document(newChannelName.get()).mutate(Channel(LastMessage("","")))
        newChannelName.set("")
    }
}

