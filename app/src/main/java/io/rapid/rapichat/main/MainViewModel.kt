package io.rapid.rapichat.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import io.rapid.rapichat.data.RapidChat
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList


class MainViewModel(application: Application) : AndroidViewModel(application) {

    val channels = DiffObservableList<ChannelItemViewModel>(object : DiffObservableList.Callback<ChannelItemViewModel> {
        override fun areContentsTheSame(item1: ChannelItemViewModel, item2: ChannelItemViewModel) = item1.doc.hasSameContentAs(item2.doc)
        override fun areItemsTheSame(item1: ChannelItemViewModel, item2: ChannelItemViewModel) = item1.doc.id == item2.doc.id
    })


    fun observe(lifecycleOwner: LifecycleOwner) {
        RapidChat.CHANNELS.liveData.observe(lifecycleOwner, Observer {
            channels.update(it!!.map { ChannelItemViewModel(it) })
        })
    }


    fun refreshUnread() {
        channels.forEach { it.refreshUnread() }
    }
}

