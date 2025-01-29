package com.c2332.adoptahuellitas

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.c2332.adoptahuellitas.activities.OpcionesLoginActivity
import com.c2332.adoptahuellitas.databinding.ActivityMainBinding
import com.c2332.adoptahuellitas.fragments.FragmentHuellitas
import com.c2332.adoptahuellitas.fragments.FragmentPerfil
import com.c2332.adoptahuellitas.fragments.FragmentSolicitudes
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_C2332NOCOUNTRY)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser == null){
            irOpcionesLogin()
        }

        // Fragmento por defecto
        verFragmentoHuellitas()

        binding.bottomNV.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.item_perfil -> {
                    verFragmentoPerfil()
                    true
                }

                R.id.item_huellitas -> {
                    verFragmentoHuellitas()
                    true
                }

                R.id.item_solicitudes -> {
                    verFragmentoSolicitudes()
                    true
                }

                else -> {
                    false
                }
            }

        }
    }

    private fun irOpcionesLogin() {
        startActivity(Intent(applicationContext, OpcionesLoginActivity::class.java))
        finishAffinity()
    }


    private fun verFragmentoPerfil(){
        binding.tvTitulo.text = "Perfil"

        val fragment = FragmentPerfil()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Perfil")
        fragmentTransaction.commit()
    }

    private fun verFragmentoHuellitas(){
        binding.tvTitulo.text = "Huellitas"

        val fragment = FragmentHuellitas()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Huellitas")
        fragmentTransaction.commit()
    }

    private fun verFragmentoSolicitudes(){
        binding.tvTitulo.text = "Solicitudes"

        val fragment = FragmentSolicitudes()
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(binding.fragmentoFL.id, fragment, "Fragment Solicitudes")
        fragmentTransaction.commit()
    }
}