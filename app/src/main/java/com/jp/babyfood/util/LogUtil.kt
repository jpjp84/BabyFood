package com.jp.babyfood.util

import android.util.Log
import com.jp.babyfood.BuildConfig

object LogUtil {
    fun <T> makeLogTag(targetClass: Class<T>): String {
        return "BF_${targetClass::class.java.simpleName}"
    }

    fun LOGI(tag: String, message: String) {
        if (!BuildConfig.DEBUG) {
            return
        }
        Log.i(tag, message)
    }
}