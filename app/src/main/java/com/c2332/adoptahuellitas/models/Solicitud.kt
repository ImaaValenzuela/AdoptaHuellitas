package com.c2332.adoptahuellitas.models

data class Solicitud(
    var id: String = "",
    var idUsuario: String = "",
    var idMascota: String = "",
    var idFormulario: String = "",
    var estado: String = "",
    var fecha: Long = System.currentTimeMillis()
)