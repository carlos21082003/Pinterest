package com.sem4.pinterestxml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Agregar : AppCompatActivity() {
    private lateinit var nombreProductoEditText: EditText
    private lateinit var descripcionProductoEditText: EditText
    private lateinit var btnGuardar: Button
    private lateinit var dbHelper: PublicacionDbHelper
    private var publicacionId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_agregar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnIrAElegir: Button = findViewById(R.id.btnIrAElegir)
        btnIrAElegir.setOnClickListener {
            // Redirige a la actividad ElegirTipo
            val intent = Intent(this, ElegirTipo::class.java)
            startActivity(intent)
            finish()
        }

        // Obtener referencias a las vistas
        nombreProductoEditText = findViewById(R.id.nProducto)
        descripcionProductoEditText = findViewById(R.id.aDescripcion)
        btnGuardar = findViewById(R.id.btnPublicar) // Usamos el mismo ID del botón en el layout

        // Inicializar la instancia de PublicacionDbHelper
        dbHelper = PublicacionDbHelper(this)

        // Verificar si se están recibiendo datos para editar
        if (intent.hasExtra("id")) {
            publicacionId = intent.getIntExtra("id", -1)
            val nombre = intent.getStringExtra("nombre")
            val descripcion = intent.getStringExtra("descripcion")

            nombreProductoEditText.setText(nombre)
            descripcionProductoEditText.setText(descripcion)
            btnGuardar.text = "Guardar Cambios" // Cambia el texto del botón
        } else {
            btnGuardar.text = "Publicar" // Texto por defecto para agregar
        }

        // Definir el OnClickListener para el botón Guardar/Publicar
        btnGuardar.setOnClickListener {
            val nombre = nombreProductoEditText.text.toString().trim()
            val descripcion = descripcionProductoEditText.text.toString().trim()

            if (nombre.isNotEmpty() && descripcion.isNotEmpty()) {
                if (publicacionId != -1) {
                    // Modo Edición
                    val publicacionActualizada = PNormal(publicacionId, nombre, descripcion)
                    val resultado = dbHelper.actualizarNota(publicacionActualizada)
                    if (resultado) {
                        Toast.makeText(this, "Publicación actualizada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al actualizar la publicación", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // Modo Agregar
                    val nuevaPublicacion = PNormal(0, nombre, descripcion) // El ID se genera automáticamente
                    val resultado = dbHelper.insertarNota(nuevaPublicacion)
                    if (resultado) {
                        Toast.makeText(this, "Publicación agregada", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Error al agregar la publicación", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, completa el nombre y la descripción", Toast.LENGTH_SHORT).show()
            }
        }
    }


}