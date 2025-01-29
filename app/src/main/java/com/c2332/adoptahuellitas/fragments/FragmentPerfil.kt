package com.c2332.adoptahuellitas.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.activities.OpcionesLoginActivity
import com.c2332.adoptahuellitas.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentPerfil : Fragment() {
    private lateinit var binding: FragmentPerfilBinding
    private lateinit var mContext: Context
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPerfilBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(mContext, OpcionesLoginActivity::class.java))
            activity?.finishAffinity()
        }
    }

}