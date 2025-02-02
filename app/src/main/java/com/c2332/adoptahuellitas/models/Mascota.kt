package com.c2332.adoptahuellitas.models

import java.io.Serializable

data class Mascota(
    var id: String = "",
    var nombre: String = "",
    var especie: String = "",
    var edad: String = "",
    var tamanio: String = "",
    var genero: String = "",
    var ubicacion: String = "",
    var estadoSalud: String = "",
    var personalidad: String = "",
    var requisitos: String = "",
    var fotoUrl: String = ""
) : Serializable