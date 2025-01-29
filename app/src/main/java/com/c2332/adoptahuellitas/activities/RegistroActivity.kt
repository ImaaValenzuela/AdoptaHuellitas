package com.c2332.adoptahuellitas.activities

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.c2332.adoptahuellitas.MainActivity
import com.c2332.adoptahuellitas.databinding.ActivityRegistroBinding
import com.c2332.adoptahuellitas.utils.Const
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.crashlytics.FirebaseCrashlytics

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {
            validarInformacion()
        }

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginEmailActivity::class.java))
        }
    }

    private var nombres = ""
    private var email = ""
    private var contrasenia = ""
    private var r_password = ""

    private fun validarInformacion() {
        nombres = binding.etName.text.toString().trim()
        email = binding.etEmail.text.toString().trim()
        contrasenia = binding.etPassword.text.toString().trim()
        r_password = binding.etRPassword.text.toString().trim()

        when {
            nombres.isEmpty() -> {
                binding.etName.error = "Ingrese nombre"
                binding.etName.requestFocus()
            }
            email.isEmpty() -> {
                binding.etEmail.error = "Ingrese email"
                binding.etEmail.requestFocus()
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.etEmail.error = "Email inválido"
                binding.etEmail.requestFocus()
            }
            contrasenia.isEmpty() -> {
                binding.etPassword.error = "Ingrese contraseña"
                binding.etPassword.requestFocus()
            }
            r_password.isEmpty() -> {
                binding.etRPassword.error = "Repita contraseña"
                binding.etRPassword.requestFocus()
            }
            contrasenia != r_password -> {
                binding.etRPassword.error = "No coinciden las contraseñas"
                binding.etRPassword.requestFocus()
            }
            else -> registrarUsuario()
        }
    }

    private fun registrarUsuario() {
        showProgress(true)
        firebaseAuth.createUserWithEmailAndPassword(email, contrasenia)
            .addOnSuccessListener {
                actualizarInfo()
            }
            .addOnFailureListener { e ->
                showProgress(false)

                // Registrar error en Crashlytics
                val crashlytics = FirebaseCrashlytics.getInstance()
                crashlytics.log("Error al registrar usuario: ${e.message}")
                crashlytics.recordException(e)

                val errorMessage = when (e) {
                    is FirebaseAuthUserCollisionException -> "Este correo ya está registrado."
                    is FirebaseAuthWeakPasswordException -> "La contraseña es demasiado débil."
                    else -> "Error al crear cuenta: ${e.message}"
                }
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarInfo() {
        val uidU = firebaseAuth.uid ?: return
        val namesU = nombres
        val emailU = firebaseAuth.currentUser?.email ?: "No disponible"
        val timeR = Const.obtenerTiempoD()

        val dataUser = mapOf(
            "uid" to uidU,
            "names" to namesU,
            "email" to emailU,
            "time" to timeR,
            "prov" to "email",
            "status" to "online",
            "image" to ""
        )

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU)
            .setValue(dataUser)
            .addOnSuccessListener {
                showProgress(false)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                showProgress(false)

                // Registrar error en Crashlytics
                val crashlytics = FirebaseCrashlytics.getInstance()
                crashlytics.log("Error al guardar información del usuario: ${e.message}")
                crashlytics.recordException(e)

                Toast.makeText(this, "Error al guardar información: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun showProgress(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
    }
}
