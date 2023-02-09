package com.example.ludometricas.jogatina.historico.detalhes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ludometricas.R
import com.example.ludometricas.data.dataClasses.NotaIndividualLocal

class NotasIndividuaisAdapter(private val notas: List<NotaIndividualLocal>, private val context: Context) :
RecyclerView.Adapter<NotasIndividuaisAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeJogador = itemView.findViewById<TextView>(R.id.txt_responsavel)
        val notaMecanica = itemView.findViewById<TextView>(R.id.nota_mecanica)
        val notaComponentes = itemView.findViewById<TextView>(R.id.nota_componentes)
        val notaExperiencia = itemView.findViewById<TextView>(R.id.nota_experiencia)
        val notaTotal = itemView.findViewById<TextView>(R.id.nota_total_historico)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_nota_individual_fragment, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val nota = notas[position]

        holder.nomeJogador.text = nota.responsavel
        holder.notaMecanica.text = nota.mecanica.round(2).toString()
        holder.notaComponentes.text = nota.componentes.round(2).toString()
        holder.notaExperiencia.text = nota.experiencia.round(2).toString()
        holder.notaTotal.text = calcularNotaTotal(nota.mecanica, nota.componentes, nota.experiencia).round(2).toString()
    }

    private fun calcularNotaTotal(mec: Double, comp: Double, exp: Double): Double {
        return (mec*0.3)+(comp*0.2)+(exp*0.5)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}