package com.example.placebit.util

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val disposables = CompositeDisposable()
    val errorMessage = ObservableField<String>("")
    open val state = MutableLiveData<StateEnum>(StateEnum.NONE)

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}