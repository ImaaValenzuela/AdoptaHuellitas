package com.c2332.adoptahuellitas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.models.Solicitud
import com.c2332.adoptahuellitas.repositories.MascotaRepository
import com.c2332.adoptahuellitas.utils.Const

class SolicitudAdapter(
    private val context: Context,
    private val solicitudes: List<Solicitud>
) : RecyclerView.Adapter<SolicitudAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textNombre: TextView = view.findViewById(R.id.textMascotaNombre)
        val textEstado: TextView = view.findViewById(R.id.textEstadoSolicitud)
        val textFecha: TextView = view.findViewById(R.id.textFechaSolicitud)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_solicitud, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val solicitud = solicitudes[position]
        val mascotaRepository = MascotaRepository()
        val nombreMascota = mascotaRepository.obtenerNombreMascotaPorId(solicitud.idMascota) ?: "Desconocido"
        val fechaFormateada = Const.formatoFecha(solicitud.fecha)

        holder.textNombre.text = "Mascota: $nombreMascota"
        holder.textEstado.text = "Estado: ${solicitud.estado}"
        holder.textFecha.text = "Fecha: $fechaFormateada"
    }

    override fun getItemCount(): Int = solicitudes.size
}
