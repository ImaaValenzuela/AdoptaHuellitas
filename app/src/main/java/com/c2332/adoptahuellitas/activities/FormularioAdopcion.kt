package com.c2332.adoptahuellitas.activities

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.c2332.adoptahuellitas.MainActivity
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.models.Formulario
import com.c2332.adoptahuellitas.models.Solicitud
import com.c2332.adoptahuellitas.utils.Const
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator

class FormularioAdopcion : AppCompatActivity() {

    private lateinit var tipoVivienda: EditText
    private lateinit var patioJardin: EditText
    private lateinit var otrosAnimales: EditText
    private lateinit var ninosHogar: EditText
    private lateinit var tiempoEnCasa: EditText
    private lateinit var mascotasAnteriores: EditText
    private lateinit var checkActualizado: CheckBox
    private lateinit var checkResponsabilidad: CheckBox
    private lateinit var checkSeguimiento: CheckBox
    private lateinit var checkCompromiso: CheckBox
    private lateinit var firmaDigital: EditText
    private lateinit var btnEnviarFormulario: MaterialButton
    private var idMascota: String? = null
    private lateinit var progressDialog: CircularProgressIndicator

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_adopcion)

        tipoVivienda = findViewById(R.id.tipo_vivienda)
        patioJardin = findViewById(R.id.patio_jardin)
        otrosAnimales = findViewById(R.id.otros_animales)
        ninosHogar = findViewById(R.id.ninos_hogar)
        tiempoEnCasa = findViewById(R.id.tiempo_en_casa)
        mascotasAnteriores = findViewById(R.id.mascotas_anteriores)
        checkActualizado = findViewById(R.id.check_actualizado)
        checkResponsabilidad = findViewById(R.id.check_responsabilidad)
        checkSeguimiento = findViewById(R.id.check_seguimiento)
        checkCompromiso = findViewById(R.id.check_compromiso)
        firmaDigital = findViewById(R.id.firma_digital)
        btnEnviarFormulario = findViewById(R.id.btn_enviar_formulario)
        progressDialog = findViewById(R.id.progressDialog)

        idMascota = intent.getStringExtra("idMascota")

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        btnEnviarFormulario.setOnClickListener {
            enviarFormulario()
        }
    }

    private fun enviarFormulario() {
        progressDialog.show()

        val tipoCasa = tipoVivienda.text.toString()
        val tieneJardin = patioJardin.text.toString()
        val tieneOtrasMascotas = otrosAnimales.text.toString()
        val tieneNinios = ninosHogar.text.toString()
        val esCasaDiaria = tiempoEnCasa.text.toString()
        val haTenidoMascotasAntes = mascotasAnteriores.text.toString()
        val aceptaPerfilActualizado = checkActualizado.isChecked
        val aceptaResponsabilidad = checkResponsabilidad.isChecked
        val aceptaSeguimiento = checkSeguimiento.isChecked
        val entiendeCompromiso = checkCompromiso.isChecked
        val firma = firmaDigital.text.toString()

        val idFormulario = database.push().key ?: ""
        val idUsuario = firebaseAuth.currentUser?.uid ?: ""
        val marcaDeTiempo = Const.obtenerTiempoD()

        val formulario = Formulario(
            idFormulario = idFormulario,
            idUsuario = idUsuario,
            idMascota = idMascota ?: "",
            tipoCasa = tipoCasa,
            tieneJardin = tieneJardin,
            tieneOtrasMascotas = tieneOtrasMascotas,
            tieneNinios = tieneNinios,
            esCasaDiaria = esCasaDiaria,
            haTenidoMascotasAntes = haTenidoMascotasAntes,
            aceptaPerfilActualizado = aceptaPerfilActualizado,
            aceptaResponsabilidad = aceptaResponsabilidad,
            aceptaSeguimiento = aceptaSeguimiento,
            entiendeCompromiso = entiendeCompromiso,
            firma = firma,
            marcaDeTiempo = marcaDeTiempo
        )

        database.child("Formularios").child(idFormulario).setValue(formulario)
            .addOnSuccessListener {
                // Crear solicitud después de guardar el formulario
                crearSolicitud(idUsuario, idMascota ?: "", idFormulario)
            }
            .addOnFailureListener { e ->
                progressDialog.hide()
                Toast.makeText(this, "Error al enviar el formulario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun crearSolicitud(idUsuario: String, idMascota: String, idFormulario: String) {
        val idSolicitud = database.push().key ?: ""
        val solicitud = Solicitud(
            id = idSolicitud,
            idUsuario = idUsuario,
            idMascota = idMascota,
            idFormulario = idFormulario,
            estado = "Pendiente",
            fecha = Const.obtenerTiempoD()
        )

        database.child("Solicitudes").child(idSolicitud).setValue(solicitud)
            .addOnSuccessListener {
                progressDialog.hide()
                Toast.makeText(this, "Solicitud enviada exitosamente", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.hide()
                Toast.makeText(this, "Error al enviar la solicitud: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
