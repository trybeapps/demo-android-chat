package io.rapid.rapichat.utils

import android.arch.lifecycle.*
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import io.rapid.rapichat.BR


class ViewModelBinding<T : ViewDataBinding, VM : ViewModel> internal constructor(val activity: FragmentActivity, val lifecycleOwner: LifecycleOwner, @LayoutRes val layoutId: Int, val viewModelClass: Class<VM>) : LifecycleObserver {

    var fragment: Fragment? = null

    constructor(fragment: Fragment, lifecycleOwner: LifecycleOwner, @LayoutRes layoutId: Int, viewModelClass: Class<VM>) : this(fragment.activity, lifecycleOwner, layoutId, viewModelClass) {
        this.fragment = fragment
    }

    lateinit var binding: T
    lateinit var viewModel: VM

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
    }

    fun initialize() {

        if (fragment != null) {
            viewModel = ViewModelProviders.of(fragment!!).get(viewModelClass)
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutId, null, false)
            binding.setVariable(BR.view, fragment)
        } else {
            viewModel = ViewModelProviders.of(activity).get(viewModelClass)
            binding = DataBindingUtil.inflate(LayoutInflater.from(activity), layoutId, null, false)
            activity.setContentView(binding.root)
            binding.setVariable(BR.view, activity)
        }

        binding.setVariable(BR.viewModel, viewModel)
    }

}