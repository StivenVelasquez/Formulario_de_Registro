package com.stivenvelasquez.formulario.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import java.util.Timer
import androidx.lifecycle.LiveData
import kotlin.concurrent.timerTask

class MainViewModel : ViewModel() {

    private val _navigateToSignUp: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val navigateToSignUp: LiveData<Boolean>
        get() = _navigateToSignUp

    private var timer: Timer? = null

    init {
        timer = Timer()
        timer?.schedule(timerTask {
            _navigateToSignUp.postValue(true)
        }, 2000)
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
        timer = null
    }
}
