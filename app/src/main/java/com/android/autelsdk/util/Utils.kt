package com.android.autelsdk.util

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan


object Utils {


    fun getSuccessShowText(
        extraText: String = "", status: String = Constants.SUCCESS,
        methodName: String = if (Thread.currentThread()
                .getStackTrace().size > 6
        ) Thread.currentThread().getStackTrace()[6].getMethodName() else ""
    ): String {
        return "$methodName() $status $extraText"
    }

    fun getAllStackTraceFunctions(): Array<out StackTraceElement> {
        return Thread.currentThread().stackTrace
    }

    fun getFailureShowText(
        extraText: String = "", status: String = Constants.FAILED,
        methodName: String = if (Thread.currentThread()
                .getStackTrace().size > 6
        ) Thread.currentThread().getStackTrace()[6].getMethodName() else ""
    ): String {
        return "$methodName() $status $extraText\n\n"
    }

    fun getColoredText (text : String, status : String) : Spannable {
        var color : Int
        if (status == Constants.SUCCESS) {
            color = Color.BLUE

        } else if (status == Constants.FAILED) {
            color = Color.RED
        } else {
            color = Color.BLACK
        }

        val texttoSpan: Spannable =
            SpannableString(text)

        texttoSpan.setSpan(
            ForegroundColorSpan(color),
            0,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return texttoSpan
    }

}