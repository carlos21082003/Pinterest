package com.sem4.pinterestxml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PAdapter (
    private var lista: List<PNormal>,
    private val onEdit: (PNormal) -> Unit,
    private val onDelete: (PNormal) -> Unit
)  : RecyclerView.Adapter<PAdapter.NotaViewHolder>(){
    class NotaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNombre: TextView = view.findViewById<TextView>(R.id.tvNombre)
        val tvContenido: TextView = view.findViewById<TextView>(R.id.tvDescripcion)
        val btnEditar: ImageButton = view.findViewById(R.id.btnEditar)
        val btnEliminar: ImageButton = view.findViewById(R.id.btnElliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.p_normal, parent, false)
        return NotaViewHolder(view)
    }

    override fun getItemCount(): Int = lista.size

    override fun onBindViewHolder(
        holder: NotaViewHolder,
        position: Int
    ) {
        val nota = lista[position]
        holder.tvNombre.setText(nota.nombre)
        holder.tvContenido.setText(nota.descripcion)

        holder.btnEditar.setOnClickListener { onEdit(nota) }
        holder.btnEliminar.setOnClickListener { onDelete(nota) }
    }

    fun actualizarLista(nuevaLista: List<PNormal>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}