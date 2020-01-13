package com.example.placebit

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.jakewharton.threetenabp.AndroidThreeTen
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApp : Application(), LifecycleObserver {

    private var inForeground = false

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val realmConfiguration = RealmConfiguration.Builder()
            .name("placebit.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.getInstance(realmConfiguration)
        Realm.setDefaultConfiguration(realmConfiguration)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        AndroidThreeTen.init(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        inForeground = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        inForeground = true
    }

    companion object {
        fun getApp(context: Context): BaseApp {
            return context.applicationContext as BaseApp
        }
    }
}