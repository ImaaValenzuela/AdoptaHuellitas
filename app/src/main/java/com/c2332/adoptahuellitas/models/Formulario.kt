package com.c2332.adoptahuellitas.models

data class Formulario (
    val idFormulario: String = "",
    val idUsuario: String = "",
    val idMascota: String = "",
    val tipoCasa: String = "",
    val tieneJardin: String = "",
    val tieneOtrasMascotas: String = "",
    val tieneNinios: String = "",
    val esCasaDiaria: String = "",
    val haTenidoMascotasAntes: String = "",
    val aceptaPerfilActualizado: Boolean = false,
    val aceptaResponsabilidad: Boolean = false,
    val aceptaSeguimiento: Boolean = false,
    val entiendeCompromiso: Boolean = false,
    val firma: String = "",
    val marcaDeTiempo: Long = System.currentTimeMillis()
)