package com.sem4.pinterestxml

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MostrarPublicacionesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PAdapter
    private lateinit var dbHelper: PublicacionDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_publicaciones)

        recyclerView = findViewById(R.id.recyclerViewPublicaciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        dbHelper = PublicacionDbHelper(this)
        val listaDePublicaciones = dbHelper.getAllNotas()

        adapter = PAdapter(listaDePublicaciones,
            onEdit = { publicacion ->
                // Lógica para editar la publicación
                val intent = Intent(this, Agregar::class.java).apply {
                    putExtra("id", publicacion.id)
                    putExtra("nombre", publicacion.nombre)
                    putExtra("descripcion", publicacion.descripcion)
                }
                startActivity(intent)
            },
            onDelete = { publicacion ->
                // Lógica para eliminar la publicación
                dbHelper.eliminarNota(publicacion.id.toString())
                actualizarListaDePublicaciones() // Recargar la lista después de eliminar
                Toast.makeText(this, "Eliminar: ${publicacion.nombre}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.adapter = adapter

        val btnIrAPerfil: Button = findViewById(R.id.btnIrAPerfil)
        btnIrAPerfil.setOnClickListener {
            // Redirige a la pestaña PerfilActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarListaDePublicaciones() // Recarga la lista cada vez que la actividad se vuelve visible
    }

    private fun actualizarListaDePublicaciones() {
        val nuevaLista = dbHelper.getAllNotas()
        adapter.actualizarLista(nuevaLista)
    }
}