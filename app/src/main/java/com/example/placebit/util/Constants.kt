package com.example.placebit.util

import androidx.annotation.StringDef


class Constants {

    companion object {

        @StringDef(CLIENT,REFRESH)
        @Retention(AnnotationRetention.SOURCE)
        annotation class GrantType
        const val CLIENT = "client_credentials"
        const val REFRESH = "refresh_token"

    }

}