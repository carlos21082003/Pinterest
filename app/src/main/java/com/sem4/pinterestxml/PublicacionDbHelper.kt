package com.sem4.pinterestxml

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.content.ContentValues
import android.util.Log

class PublicacionDbHelper(
    context: Context
): SQLiteOpenHelper(
    context, DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "notas_data_base"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "notas_table"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_DESCRIPCION = "descripcion"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_DESCRIPCION TEXT
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }

    fun insertarNota(nota: PNormal): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nota.nombre)
            put(COLUMN_DESCRIPCION, nota.descripcion)
        }
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.d("MiApp", "Creando nota: $result")
        return result != -1L
    }


    // Obtener todas las notas
    fun getAllNotas(): List<PNormal> {
        val lista = mutableListOf<PNormal>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val nota = PNormal(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                    descripcion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION)),
                )
                lista.add(nota)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return lista
    }


    fun getNotaID(id: String): PNormal? {
        val db = readableDatabase
        var nota: PNormal? = null
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE id = ?", arrayOf(id))

        if (cursor.moveToFirst()) {
            nota = PNormal(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPCION))
            )
        }

        cursor.close()
        db.close()
        return nota
    }


    fun actualizarNota(nota: PNormal): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nota.nombre)
            put(COLUMN_DESCRIPCION, nota.descripcion)
        }
        val result = db.update(TABLE_NAME, values, "id = ?", arrayOf(nota.id.toString()))
        db.close()
        Log.d("MiApp", "Actualizando nota: $result")
        return result > 0
    }

    fun eliminarNota(id: String): Boolean {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "id = ?", arrayOf(id))
        db.close()
        return result > 0
    }

}
