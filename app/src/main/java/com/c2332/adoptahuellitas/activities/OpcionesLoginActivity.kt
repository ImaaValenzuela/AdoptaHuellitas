package com.c2332.adoptahuellitas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.c2332.adoptahuellitas.MainActivity
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.databinding.ActivityOpcionesLoginBinding
import com.c2332.adoptahuellitas.utils.Const
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.crashlytics.FirebaseCrashlytics

class OpcionesLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var progressDialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        setupProgressDialog()
        setupGoogleSignIn()
        comprobarSesion()

        binding.opcionEmail.setOnClickListener {
            startActivity(Intent(applicationContext, LoginEmailActivity::class.java))
        }

        binding.opcionGoogle.setOnClickListener {
            iniciarGoogle()
        }
    }

    private fun setupProgressDialog() {
        val progressIndicator = CircularProgressIndicator(this)
        progressIndicator.isIndeterminate = true

        progressDialog = AlertDialog.Builder(this)
            .setView(progressIndicator)
            .setCancelable(false)
            .create()
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun iniciarGoogle() {
        val googleSignInIntent = mGoogleSignInClient.signInIntent
        googleSignInARL.launch(googleSignInIntent)
    }

    private val googleSignInARL = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode == RESULT_OK) {
            val data = resultado.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val cuenta = task.getResult(ApiException::class.java)
                autenticarCuentaGoogle(cuenta.idToken)
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
        }
    }

    private fun autenticarCuentaGoogle(idToken: String?) {
        val credencial = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credencial)
            .addOnSuccessListener { authResultado ->
                if (authResultado.additionalUserInfo!!.isNewUser) {
                    actualizarInfoUsuario()
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener { e ->
                FirebaseCrashlytics.getInstance().recordException(e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarInfoUsuario() {
        progressDialog.show()

        val uidU = firebaseAuth.uid
        val nombresU = firebaseAuth.currentUser?.displayName
        val emailU = firebaseAuth.currentUser?.email
        val tiempoR = Const.obtenerTiempoD()

        val datosUsuario = hashMapOf(
            "uid" to (uidU ?: ""),
            "nombres" to (nombresU ?: ""),
            "email" to (emailU ?: ""),
            "tiempoR" to tiempoR,
            "proveedor" to "Google",
            "estado" to "Online",
            "imagen" to ""
        )

        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidU!!)
            .setValue(datosUsuario)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                FirebaseCrashlytics.getInstance().recordException(e)
                Toast.makeText(this, "Error al guardar la cuenta: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun comprobarSesion() {
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}
