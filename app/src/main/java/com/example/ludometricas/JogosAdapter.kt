package com.example.ludometricas

import android.content.Context
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ludometricas.data.Jogo

class JogosAdapter(private val jogos: List<Jogo>,
                   private val listener: (Jogo) -> Unit,
                   private val context: Context
): RecyclerView.Adapter<JogosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo = itemView.findViewById(R.id.textView) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_jogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jogo = jogos[position]
        holder.txtTitulo.text = jogo.nome

        holder.itemView.setOnClickListener { listener(jogo) }
    }

    override fun getItemCount(): Int {
        return jogos.size
    }
}