package com.sem4.pinterestxml

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Prefs(context: Context) {
    val SHARED_DBNAME = "dblocal"
    val SHARED_USUARIO = "Usuario"
    val SHARED_CLAVE = "Clave"

    val storage = context.getSharedPreferences(SHARED_DBNAME, 0)

    fun saveUsuario(usuario: String) {
        storage.edit().putString(SHARED_USUARIO, usuario).apply()
    }

    fun saveClave(clave: String) {
        storage.edit().putString(SHARED_CLAVE, clave).apply()
    }

    fun getUsuario():String {
        return storage.getString(SHARED_USUARIO, "")!!
    }

    fun getClave():String {
        return storage.getString(SHARED_CLAVE, "")!!
    }

    fun borarDB() {
        storage.edit().clear().apply()
    }

}