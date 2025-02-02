package com.c2332.adoptahuellitas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.models.Mascota
import com.c2332.adoptahuellitas.viewmodels.MascotasViewModel
import com.google.android.material.button.MaterialButton

class MascotaDetalleActivity : AppCompatActivity() {
    private val viewModel: MascotasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mascota_detalle)

        val mascotaFoto: ImageView = findViewById(R.id.mascota_foto)
        val mascotaNombre: TextView = findViewById(R.id.mascota_nombre)
        val mascotaEspecie: TextView = findViewById(R.id.mascota_especie)
        val mascotaEdad: TextView = findViewById(R.id.mascota_edad)
        val mascotaTamanio: TextView = findViewById(R.id.mascota_tamanio)
        val mascotaGenero: TextView = findViewById(R.id.mascota_genero)
        val mascotaUbicacion: TextView = findViewById(R.id.mascota_ubicacion)
        val mascotaEstadoSalud: TextView = findViewById(R.id.mascota_estado_salud)
        val mascotaPersonalidad: TextView = findViewById(R.id.mascota_personalidad)
        val mascotaRequisitos: TextView = findViewById(R.id.mascota_requisitos)
        val btnAplicar: MaterialButton = findViewById(R.id.btn_aplicar)

        val mascota = intent.getSerializableExtra("mascota") as? Mascota
        mascota?.let { viewModel.setMascota(it) }

        viewModel.mascota.observe(this) { mascota ->
            mascotaNombre.text = mascota.nombre
            mascotaEspecie.text = "Especie: ${mascota.especie}"
            mascotaEdad.text = "Edad: ${mascota.edad}"
            mascotaTamanio.text = "Tamaño: ${mascota.tamanio}"
            mascotaGenero.text = "Género: ${mascota.genero}"
            mascotaUbicacion.text = "Ubicación: ${mascota.ubicacion}"
            mascotaEstadoSalud.text = "Estado de salud: ${mascota.estadoSalud}"
            mascotaPersonalidad.text = "Personalidad: ${mascota.personalidad}"
            mascotaRequisitos.text = "Requisitos: ${mascota.requisitos}"

            Glide.with(this).load(mascota.fotoUrl).into(mascotaFoto)

            btnAplicar.setOnClickListener {
                val intent = Intent(this, FormularioAdopcion::class.java)
                intent.putExtra("idMascota", mascota.id)
                startActivity(intent)
            }
        }
    }
}
