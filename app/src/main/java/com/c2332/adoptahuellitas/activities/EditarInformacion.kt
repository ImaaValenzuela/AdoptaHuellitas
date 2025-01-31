package com.c2332.adoptahuellitas.activities

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.c2332.adoptahuellitas.R
import com.c2332.adoptahuellitas.databinding.ActivityEditarInformacionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONObject
import java.io.IOException

class EditarInformacion : AppCompatActivity() {

    private lateinit var binding : ActivityEditarInformacionBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog : ProgressDialog

    private var imagenUri : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarInformacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        cargarInformacion()

        binding.IbRegresar.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.IvEditarImg.setOnClickListener {
            Toast.makeText(this, "Proximamente", Toast.LENGTH_SHORT).show()
            /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                abrirGaleria()
            }else{
                solicitarPermisoAlmacenamiento.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }*/
        }

        binding.btnActualizar.setOnClickListener {
            validarInformacion()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galeriaARL.launch(intent)
    }

    private val galeriaARL =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == Activity.RESULT_OK && resultado.data?.data != null) {
                imagenUri = resultado.data?.data
                binding.ivPerfil.setImageURI(imagenUri)
                subirImagenACloudinary(imagenUri)
            } else {
                Toast.makeText(this, "Selección cancelada", Toast.LENGTH_SHORT).show()
            }
        }


    private fun subirImagenACloudinary(imagenUri: Uri?) {
        if (imagenUri == null) {
            Toast.makeText(this, "Error: No se seleccionó ninguna imagen", Toast.LENGTH_SHORT).show()
            return
        }

        progressDialog.setMessage("Subiendo imagen...")
        progressDialog.show()

        val cloudName = "dureczpek"
        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("upload_preset", "ml_default")

        try {
            val inputStream = contentResolver.openInputStream(imagenUri)
            val bytes = inputStream?.readBytes()
            inputStream?.close()

            if (bytes != null) {
                requestBodyBuilder.addFormDataPart(
                    "file", "imagen.jpg",
                    RequestBody.create("image/*".toMediaTypeOrNull(), bytes)
                )
            } else {
                throw IOException("No se pudo leer la imagen")
            }

        } catch (e: IOException) {
            progressDialog.dismiss()
            Toast.makeText(this, "Error al leer la imagen: ${e.message}", Toast.LENGTH_SHORT).show()
            return
        }

        val requestBody = requestBodyBuilder.build()
        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
            .post(requestBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    val jsonResponse = JSONObject(it)
                    val imageUrl = jsonResponse.optString("secure_url", "")

                    if (imageUrl.isNotEmpty()) {
                        runOnUiThread {
                            progressDialog.dismiss()
                            actualizarInfoBD(imageUrl)  // Guarda la URL en Firebase
                        }
                    } else {
                        runOnUiThread {
                            progressDialog.dismiss()
                            Toast.makeText(applicationContext, "No se obtuvo URL de la imagen", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }




    private fun actualizarInfoBD(urlImagen: String) {
        val uid = firebaseAuth.uid
        if (uid == null) {
            Toast.makeText(this, "Error: Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)
        val hashMap = hashMapOf("imagen" to urlImagen)

        ref.updateChildren(hashMap as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(this, "Imagen actualizada correctamente", Toast.LENGTH_SHORT).show()
                // Actualizar imagen en la UI
                Glide.with(this).load(urlImagen).into(binding.ivPerfil)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al actualizar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private val solicitarPermisoAlmacenamiento =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){ esConcedido->
            if (esConcedido){
                abrirGaleria()
            }else{
                Toast.makeText(
                    this,
                    "El permiso de almacenamiento ha sido denegado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private var nombres = ""
    private fun validarInformacion() {
        nombres = binding.etNombres.text.toString().trim()

        if (nombres.isEmpty()){
            binding.etNombres.error = "Ingrese nombres"
            binding.etNombres.requestFocus()
        }else{
            actualizarInfo()
        }
    }

    private fun actualizarInfo() {
        progressDialog.setMessage("Actualizando información")
        progressDialog.show()

        val hashMap : HashMap<String, Any> = HashMap()

        hashMap["nombres"] = nombres

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(firebaseAuth.uid!!)
            .updateChildren(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "Se actualizó su información",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


    }

    private fun cargarInformacion() {
        val uid = firebaseAuth.uid
        if (uid == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("Usuarios").child(uid)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val nombres = snapshot.child("nombres").value?.toString() ?: ""
                val imagen = snapshot.child("imagen").value?.toString() ?: ""

                binding.etNombres.setText(nombres)

                Glide.with(this@EditarInformacion)
                    .load(imagen)
                    .placeholder(R.drawable.ic_perfil2)
                    .into(binding.ivPerfil)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Error de Firebase: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}