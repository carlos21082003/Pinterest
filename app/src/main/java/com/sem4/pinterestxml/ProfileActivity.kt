package com.sem4.pinterestxml

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sem4.pinterestxml.SPrefApplication.Companion.prefs

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initUI()
    }

    private fun initUI() {
        val tvUsuario = findViewById<TextView>(R.id.tvUsuario)
        val tvClave = findViewById<TextView>(R.id.tvClave)

        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnHome = findViewById<Button>(R.id.btnHome)

        btnCerrarSesion.setOnClickListener {
            cerrarSesion()
        }

        btnAgregar.setOnClickListener {
            irAgregar()
        }

        btnHome.setOnClickListener {
            irHome()
        }
        tvUsuario.text = prefs.getUsuario()
        tvClave.text = prefs.getClave()


    }

    private fun cerrarSesion() {
        prefs.borarDB()
        irLogin()
    }

    private fun irLogin() {
        startActivity(Intent(this, MainActivity::class.java))
    }


    private fun irAgregar() {
        startActivity(Intent(this, ElegirTipo::class.java))
    }

    private fun irHome() {
        startActivity(Intent(this, MostrarPublicacionesActivity::class.java))
    }
}