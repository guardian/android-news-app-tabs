package com.theguardian.navigation.ui.sfl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SavedForLaterViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is saved for later"
    }
    val text: LiveData<String> = _text
}