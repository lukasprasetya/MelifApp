package com.binar.melif.utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.binar.melif.R


class ButtonEnablerTextWatcher(private val context: Context, private val button: ImageView) :
    TextWatcher {
    override fun onTextChanged(charSequence: CharSequence, start: Int, count: Int, after: Int) {
        if (charSequence.toString().trim().isNotEmpty()) {
            button.isEnabled = true
            button.setColorFilter(
                ContextCompat.getColor(context, R.color.black),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )

        } else {
            button.isEnabled = false
            button.setColorFilter(
                ContextCompat.getColor(context, R.color.grey),
                android.graphics.PorterDuff.Mode.MULTIPLY
            )
        }
    }

    override fun beforeTextChanged(charSequence: CharSequence?, i: Int, i1: Int, i2: Int) {}
    override fun afterTextChanged(editable: Editable) {}
}
