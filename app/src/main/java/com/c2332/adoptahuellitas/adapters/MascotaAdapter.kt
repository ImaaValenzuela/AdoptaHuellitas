package com.c2332.adoptahuellitas.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.models.Mascota

class MascotaAdapter(
    private val context: Context,
    private val mascotas: List<Mascota>
) : RecyclerView.Adapter<MascotaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageMascota: ImageView = view.findViewById(R.id.imageMascota)
        val textNombre: TextView = view.findViewById(R.id.textNombre)
        val textEspecie: TextView = view.findViewById(R.id.textEspecie)
        val iconGenero: ImageView = view.findViewById(R.id.iconGenero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_mascota, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val mascota = mascotas[position]
        holder.textNombre.text = mascota.nombre
        holder.textEspecie.text = mascota.especie

        if (mascota.genero == "Hembra") {
            holder.iconGenero.setImageResource(R.drawable.ic_female)
            holder.iconGenero.visibility = View.VISIBLE
        } else if (mascota.genero == "Macho") {
            holder.iconGenero.setImageResource(R.drawable.ic_male)
            holder.iconGenero.visibility = View.VISIBLE
        } else {
            holder.iconGenero.visibility = View.GONE
        }

        Glide.with(context)
            .load(mascota.fotoUrl)
            .placeholder(R.drawable.ic_mascota_placeholder)
            .into(holder.imageMascota)
    }

    override fun getItemCount(): Int = mascotas.size
}