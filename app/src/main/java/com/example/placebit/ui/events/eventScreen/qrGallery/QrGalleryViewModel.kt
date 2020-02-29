package com.example.placebit.ui.events.eventScreen.qrGallery

import android.app.Application
import com.example.placebit.util.BaseViewModel
import com.example.placebit.util.StateEnum

class QrGalleryViewModel(var context: Application) : BaseViewModel(context) {

    fun close() {
        state.value = StateEnum.COMPLETE
    }
}