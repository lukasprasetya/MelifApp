package com.binar.melif.utils

import android.content.Context
import com.binar.melif.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class Extensions {

    fun Long.thousandsSeparator(context: Context): String {
        val symbols = DecimalFormatSymbols()
        symbols.groupingSeparator = ",".toCharArray()[0]
        return DecimalFormat("#,###", symbols).format(this)
    }
}