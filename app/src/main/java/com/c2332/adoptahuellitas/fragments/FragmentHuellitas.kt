package com.c2332.adoptahuellitas.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.adapters.MascotaAdapter
import com.c2332.adoptahuellitas.databinding.FragmentHuellitasBinding
import com.c2332.adoptahuellitas.models.Mascota
import com.c2332.adoptahuellitas.repositories.MascotaRepository

class FragmentHuellitas : Fragment() {

    private lateinit var binding: FragmentHuellitasBinding
    private lateinit var mContext: Context
    private lateinit var mascotaAdapter: MascotaAdapter
    private var listMascota : List<Mascota> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHuellitasBinding.inflate(inflater, container, false)

        binding.RVmascotas.setHasFixedSize(true)
        binding.RVmascotas.layoutManager = GridLayoutManager(mContext, 2)

        listaMascotas()

        return binding.root
    }

    private fun listaMascotas() {
        listMascota = MascotaRepository().getMascotas()
        mascotaAdapter = MascotaAdapter(mContext, listMascota)
        binding.RVmascotas.adapter = mascotaAdapter
    }
}
