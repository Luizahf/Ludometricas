package com.example.ludometricas.presentation.jogo.historico;

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogatina

class HistoricoAdapter(private val jogatinas: List<Jogatina>,
                       private val listener: (Jogatina) -> Unit,
                       private val context: Context
): RecyclerView.Adapter<HistoricoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notaMecanica = itemView.findViewById(R.id.nota_mecanica) as TextView
        val notaComponentes = itemView.findViewById(R.id.nota_componentes) as TextView
        val notaExperiencia = itemView.findViewById(R.id.nota_experiencia) as TextView
        val notaTotal = itemView.findViewById(R.id.nota_total_historico) as TextView
        val dataHistorico = itemView.findViewById(R.id.txt_data_historico) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_hiistorico_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jogatina = jogatinas[position]

        holder.notaMecanica.text = ((jogatina.notasIndividuais.sumOf { nota -> nota.nota.mecanica })/jogatina.notasIndividuais.size).round(2).toString()
        holder.notaComponentes.text = ((jogatina.notasIndividuais.sumOf { nota -> nota.nota.componentes })/jogatina.notasIndividuais.size).round(2).toString()
        holder.notaExperiencia.text = ((jogatina.notasIndividuais.sumOf { nota -> nota.nota.experiencia })/jogatina.notasIndividuais.size).round(2).toString()
        holder.notaTotal.text = ((jogatina.notasIndividuais.sumOf { nota -> nota.nota.total })/jogatina.notasIndividuais.size).round(2).toString()
        holder.dataHistorico.text = jogatina.data

        holder.itemView.setOnClickListener {
            listener(jogatina)
        }
    }
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }

    override fun getItemCount(): Int {
        return jogatinas.size
    }

}