package io.rapid.rapichat.ui.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import io.rapid.rapichat.BR
import io.rapid.rapichat.R
import io.rapid.rapichat.databinding.ActivityMainBinding
import io.rapid.rapichat.ui.channel.ChannelFragment
import io.rapid.rapichat.ui.channel.ChannelListener
import io.rapid.rapichat.utils.*
import me.tatarka.bindingcollectionadapter2.ItemBinding

class MainActivity : LifecycleAppCompatActivity(), ChannelListener {

    val viewModelBinding = ViewModelBinding<ActivityMainBinding, MainViewModel>(this, this, R.layout.activity_main, MainViewModel::class.java)
    private lateinit var drawerToggle: ActionBarDrawerToggle
    val channelItemBinding: ItemBinding<ChannelItemViewModel> = ItemBinding.of<ChannelItemViewModel>(BR.viewModel, R.layout.item_channel).bindExtra(BR.listener, this)
    var lastChannel by stringPref("last_channel", "general")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelBinding.initialize()

        // setup toolbar and drawer
        setSupportActionBar(viewModelBinding.binding.toolbar)
        val icon = getCompatDrawable(R.drawable.ic_menu)
        icon.setCompatTint(getCompatColor(R.color.text))
        supportActionBar?.setHomeAsUpIndicator(icon)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerToggle = ActionBarDrawerToggle(this, viewModelBinding.binding.drawerLayout, R.string.open_channels, R.string.close_channels)
        viewModelBinding.binding.drawerLayout.addDrawerListener(drawerToggle)
        viewModelBinding.viewModel.observe(this)
        if (lastChannel != null) {
            onChannelClicked(lastChannel)
        }

        Snackbar.make(viewModelBinding.binding.root, getString(R.string.welcome, User.NAME), Snackbar.LENGTH_LONG).show()
    }

    override fun onChannelClicked(channelId: String) {
        supportActionBar?.title = "#${channelId}"
        supportFragmentManager.beginTransaction().replace(R.id.container, ChannelFragment.Companion.newInstance(channelId)).commit()
        viewModelBinding.binding.drawerLayout.closeDrawers()
        lastChannel = channelId
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
