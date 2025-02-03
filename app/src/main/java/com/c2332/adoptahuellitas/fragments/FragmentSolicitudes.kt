package com.c2332.adoptahuellitas.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.adapters.SolicitudAdapter
import com.c2332.adoptahuellitas.models.Solicitud
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class FragmentSolicitudes : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SolicitudAdapter
    private lateinit var solicitudes: MutableList<Solicitud>
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_solicitudes, container, false)
        recyclerView = view.findViewById(R.id.RVsolicitudes)
        recyclerView.layoutManager = LinearLayoutManager(context)

        solicitudes = mutableListOf()
        adapter = SolicitudAdapter(requireContext(), solicitudes)
        recyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().reference.child("Solicitudes")
        auth = FirebaseAuth.getInstance()

        cargarSolicitudes()

        return view
    }

    private fun cargarSolicitudes() {
        val usuarioId = auth.currentUser?.uid ?: return

        database.orderByChild("idUsuario").equalTo(usuarioId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    solicitudes.clear()
                    for (solicitudSnapshot in snapshot.children) {
                        val solicitud = solicitudSnapshot.getValue(Solicitud::class.java)
                        solicitud?.let { solicitudes.add(it) }
                    }
                    adapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Manejo de errores
                }
            })
    }
}
