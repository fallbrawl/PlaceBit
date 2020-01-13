package com.example.placebit.util

import android.util.Log
import com.example.placebit.BuildConfig

object Logs {

    fun d(message: String) {
        if (BuildConfig.DEBUG)
            Log.d("CRM", message)
    }
}
