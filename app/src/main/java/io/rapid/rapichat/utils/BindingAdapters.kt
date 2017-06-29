package io.rapid.rapichat.utils

import android.databinding.BindingAdapter
import android.view.View


@android.databinding.BindingAdapter("imageUrl")
fun setImageUrl(imageView: android.widget.ImageView, url: String?) {
    com.bumptech.glide.Glide.with(imageView.context)
            .load(url)
            .into(imageView)
}

@BindingAdapter("show")
fun setShow(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}