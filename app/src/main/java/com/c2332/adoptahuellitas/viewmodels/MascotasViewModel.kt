package com.c2332.adoptahuellitas.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.c2332.adoptahuellitas.models.Mascota

class MascotasViewModel : ViewModel() {

    private val _mascota = MutableLiveData<Mascota>()
    val mascota: LiveData<Mascota> get() = _mascota

    fun setMascota(mascota: Mascota) {
        _mascota.value = mascota
    }
}