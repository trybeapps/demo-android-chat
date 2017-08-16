package io.rapid.rapichat.utils

import android.databinding.BindingAdapter
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText


@BindingAdapter("show")
fun setShow(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}


interface OnDoneListener {
    fun onDone()
}

@BindingAdapter("onDone")
fun setOnDoneListener(editText: EditText, listener: OnDoneListener?) {
    if (listener != null) {
        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEARCH
                    || event != null && event.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                listener.onDone()
                true
            }
            false
        }
    } else {
        editText.setOnEditorActionListener(null)
    }
}