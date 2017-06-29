package io.rapid.rapichat.channel

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import io.rapid.RapidDocument
import io.rapid.rapichat.BR
import io.rapid.rapichat.R
import io.rapid.rapichat.data.Message
import io.rapid.rapichat.databinding.FragmentChannelBinding
import io.rapid.rapichat.main.MainViewModel
import io.rapid.rapichat.utils.ViewModelBinding
import me.tatarka.bindingcollectionadapter2.ItemBinding


class ChannelFragment : LifecycleFragment() {
    companion object {
        const val ARG_CHANNEL_ID = "channel_id"
        fun newInstance(channelId: String) = ChannelFragment().apply {
            arguments = android.os.Bundle()
            arguments.putString(ChannelFragment.Companion.ARG_CHANNEL_ID, channelId)
        }
    }

    private lateinit var viewModelBinding: ViewModelBinding<FragmentChannelBinding, ChannelViewModel>
    val messageItemBinding: ItemBinding<RapidDocument<Message>> = ItemBinding.of(BR.item, R.layout.item_message)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        viewModelBinding = ViewModelBinding<FragmentChannelBinding, ChannelViewModel>(this, this, R.layout.fragment_channel, ChannelViewModel::class.java)
        viewModelBinding.initialize()
        viewModelBinding.viewModel.channelId = arguments.getString(ChannelFragment.Companion.ARG_CHANNEL_ID)
        viewModelBinding.viewModel.observe(this)

        viewModelBinding.viewModel.messagesListener = {
            ViewModelProviders.of(activity).get(MainViewModel::class.java).refreshUnread()
            viewModelBinding.binding.messages.scrollToPosition(viewModelBinding.binding.messages.adapter.itemCount - 1)
        }
        return viewModelBinding.binding.root

    }
}