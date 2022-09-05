package com.android.autelsdk.util

object Utils {


    fun getSuccessShowText(
        extraText: String = "", status: String = Constants.SUCCESS,
        methodName: String = if (Thread.currentThread()
                .getStackTrace().size > 6
        ) Thread.currentThread().getStackTrace()[6].getMethodName() else ""
    ): String {
        return "$methodName() $status $extraText"
    }

    fun getFailureShowText(
        extraText: String = "", status: String = Constants.FAILED,
        methodName: String = if (Thread.currentThread()
                .getStackTrace().size > 6
        ) Thread.currentThread().getStackTrace()[6].getMethodName() else ""
    ): String {
        return "$methodName() $status $extraText\n\n"
    }

}