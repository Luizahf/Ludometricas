package com.example.ludometricas.presentation.jogo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo

class JogosAdapter(private val jogos: List<Jogo>,
                   private val listener: (Int) -> Unit,
                   private val context: Context
): RecyclerView.Adapter<JogosAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtTitulo = itemView.findViewById(R.id.txt_game_item) as TextView
        val notaTotal = itemView.findViewById(R.id.nota_total_main) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_jogo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jogo = jogos[position]
        holder.txtTitulo.text = "${jogo.posicaoNota}.  ${jogo.nome}"
        holder.notaTotal.text = jogo.notaMediaAteOMomento.total.round(2).toString()

        holder.itemView.setOnClickListener { listener(position) }
    }

    override fun getItemCount(): Int {
        return jogos.size
    }
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}