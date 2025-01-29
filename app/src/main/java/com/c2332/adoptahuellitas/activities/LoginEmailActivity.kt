package com.c2332.adoptahuellitas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.c2332.adoptahuellitas.MainActivity
import com.c2332.adoptahuellitas.databinding.ActivityLoginEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.launch

class LoginEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnEnter.setOnClickListener {
            validarInformacion()
        }

        binding.tvRegister.setOnClickListener {
            startActivity(Intent(applicationContext, RegistroActivity::class.java))
        }
    }

    private fun validarInformacion() {
        val email = binding.etEmail.text.toString().trim()
        val contrasenia = binding.etPassword.text.toString().trim()

        when {
            email.isEmpty() -> {
                binding.etEmail.error = "Ingrese email"
                binding.etEmail.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.etEmail.error = "Email no válido"
                binding.etEmail.requestFocus()
            }
            contrasenia.isEmpty() -> {
                binding.etPassword.error = "Ingrese contraseña"
                binding.etPassword.requestFocus()
            }
            else -> {
                logearUsuario(email, contrasenia)
            }
        }
    }

    private fun logearUsuario(email: String, contrasenia: String) {
        binding.progressIndicator.visibility = View.VISIBLE
        binding.btnEnter.isEnabled = false

        lifecycleScope.launch {
            firebaseAuth.signInWithEmailAndPassword(email, contrasenia)
                .addOnSuccessListener {
                    binding.progressIndicator.visibility = View.GONE
                    startActivity(Intent(this@LoginEmailActivity, MainActivity::class.java))
                    finishAffinity()
                }
                .addOnFailureListener { e ->
                    binding.progressIndicator.visibility = View.GONE
                    binding.btnEnter.isEnabled = true
                    Toast.makeText(
                        this@LoginEmailActivity,
                        "No se realizó el inicio de sesión: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    FirebaseCrashlytics.getInstance().log("Error en inicio de sesión")
                    FirebaseCrashlytics.getInstance().recordException(e)
                }
        }
    }
}
