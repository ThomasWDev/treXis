package com.technologies.mobileexercise.core.util

import android.os.SystemClock
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


/** Thomas Woodfin
 * Utility class to contain functions for custom binding implementation
 */


@BindingAdapter("error")
fun setError(editText: EditText, errorMessage: String??) {
    errorMessage?.let {
        if (editText.isFocused) {
            editText.setError(it)
        }
    } ?: run { editText.setError(null) }
}

@BindingAdapter("error")
fun setError(inputLayout: TextInputEditText, errorMessage: String??) {
    errorMessage?.let {
        if(inputLayout.isFocused) {
            (inputLayout.parent.parent as TextInputLayout).error = it
        }
    } ?: run { (inputLayout.parent.parent as TextInputLayout).error = null }
}

@BindingAdapter("android:visibility")
fun setVisibility(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

@BindingAdapter("visibilityInv")
fun setVisibilityInv(view: View, visible: Boolean?) {
    view.visibility = if (visible == true) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("clickWithDebounce")
fun setDebounceClickListener(view: View, onClickListener: View.OnClickListener) {
    val debounceTime = 1000L
    var lastClickTime: Long = 0

    val clickWithDebounce: (view: View) -> Unit = {
        if (SystemClock.elapsedRealtime() - lastClickTime >= debounceTime) {
            onClickListener.onClick(
                    it
            )
            lastClickTime = SystemClock.elapsedRealtime()
        }
    }

    view.setOnClickListener(clickWithDebounce)
}