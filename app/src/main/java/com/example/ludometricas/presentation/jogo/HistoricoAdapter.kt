package com.example.ludometricas.presentation.jogo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import java.text.SimpleDateFormat

class HistoricoAdapter(private val jogo: JogoLocal,
                       val clickListenerJogar: () -> Unit,
                       private val context: Context
): RecyclerView.Adapter<HistoricoAdapter.ViewHolder>() {
    private val caixaJogoNaoJogadoVisibility = !(jogo.jogatinas > 0) && !(jogo.RecordeData != null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoAdapter.ViewHolder {
        if (caixaJogoNaoJogadoVisibility) {
            val view = LayoutInflater.from(context).inflate(R.layout.jogo_nao_jogado, parent, false)
            return ViewHolder(view, null)
        }

        val view = LayoutInflater.from(context).inflate(R.layout.historico_jogo, parent, false)
        val view2 = LayoutInflater.from(context).inflate(R.layout.caixa_recorde, parent, false)
        return ViewHolder(view, view2)
    }

    override fun onBindViewHolder(holder: HistoricoAdapter.ViewHolder, position: Int) {
        if (!caixaJogoNaoJogadoVisibility) {
            val tempoMedioTxt = if (jogo.tempoMedioJogatina != null) "${(jogo.tempoMedioJogatina!!.toInt() / 3600)}h ${(jogo.tempoMedioJogatina!!.toInt() / 60)}min" else ""
            val recordeDate: String = (if (jogo.RecordeData != null) SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(jogo.RecordeData)) else "")

            holder.titulo_jogo.text = jogo.nome
            holder.nota_total_txt!!.text = jogo.notaMediaAteOMomento.round(2).toString()
            holder.pontuacao_recorde_txt!!.text = jogo.RecordePontuacao.toString()
            holder.responsavel_recorde_txt!!.text = jogo.RecordeResponsavel
            holder.data_recorde_txt!!.text = recordeDate
            holder.nota_mecanica_pl1!!.text = jogo.notaMecanicaMediaAteOMomento.round(2).toString()
            holder.nota_componentes!!.text = jogo.notaComponentesMediaAteOMomento.round(2).toString()
            holder.nota_experiencia!!.text = jogo.notaExperienciaMediaAteOMomento.round(2).toString()
            holder.jogatinas!!.text = jogo.jogatinas.toString()
            holder.tempo_medio!!.text = tempoMedioTxt

            holder.btn_ir_historico!!.setOnClickListener {
                clickListenerJogar()
            }
        }
    }

    override fun getItemCount(): Int {
        return 0
    }

    class ViewHolder(itemView: View, itemView2: View?) : RecyclerView.ViewHolder(itemView) {        val titulo_jogo = itemView.findViewById(R.id.titulo_jogo) as TextView
        val pontuacao_recorde_txt = if (itemView2 != null) itemView2.findViewById(R.id.pontuacao_recorde_txt) as TextView else null
        val responsavel_recorde_txt = if (itemView2 != null) itemView2.findViewById(R.id.responsavel_recorde_txt) as TextView else null
        val data_recorde_txt = if (itemView2 != null) itemView2.findViewById(R.id.data_recorde_txt) as TextView else null

        val nota_total_txt = if (itemView2 != null) itemView.findViewById(R.id.nota_total_txt) as TextView else null
        val nota_mecanica_pl1 = if (itemView2 != null) itemView.findViewById(R.id.nota_mecanica_pl1) as TextView else null
        val nota_componentes = if (itemView2 != null) itemView.findViewById(R.id.nota_componentes) as TextView else null
        val nota_experiencia = if (itemView2 != null) itemView.findViewById(R.id.nota_experiencia) as TextView else null
        val jogatinas = if (itemView2 != null) itemView.findViewById(R.id.jogatinas) as TextView else null
        val tempo_medio = if (itemView2 != null) itemView.findViewById(R.id.tempo_medio) as TextView else null
        val btn_ir_historico = if (itemView2 != null) itemView.findViewById<Button>(R.id.btn_ir_historico) else null
    }
}

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return kotlin.math.round(this * multiplier) / multiplier
}