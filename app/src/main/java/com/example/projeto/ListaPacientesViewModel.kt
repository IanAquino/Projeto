package com.example.projeto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListaPacientesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Paciente Fragment"
    }
    val text: LiveData<String> = _text
}