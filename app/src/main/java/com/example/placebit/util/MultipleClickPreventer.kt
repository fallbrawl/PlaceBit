package com.example.placebit.util

import android.os.SystemClock

object MultipleClickPreventer {
    private var elapsedRealtime: Long = 0

    fun allowClick(): Boolean {
        if (SystemClock.elapsedRealtime() - elapsedRealtime < 500) {
            return false
        }
        elapsedRealtime = SystemClock.elapsedRealtime()
        return true
    }
}
