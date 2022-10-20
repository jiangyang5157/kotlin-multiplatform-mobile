package com.gmail.jiangyang5157.demo_compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _isDarkMode = MutableLiveData(false)
    val isDarkMode: LiveData<Boolean>
        get() = _isDarkMode

    fun darkMode() {
        viewModelScope.launch { _isDarkMode.value = true }
    }

    fun lightMode() {
        viewModelScope.launch { _isDarkMode.value = false }
    }
}
