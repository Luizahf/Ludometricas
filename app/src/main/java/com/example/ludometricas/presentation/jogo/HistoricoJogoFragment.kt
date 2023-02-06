package com.example.ludometricas.presentation.jogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ludometricas.R

class HistoricoJogoFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.historico_jogo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        if (bundle != null) {
            val jogatinas = bundle.getString("jogatinas")
            if (jogatinas!!.toInt() == 1) {
                view.findViewById<TextView>(R.id.txt_nota_geral_2).text = "Foi apenas "
                view.findViewById<TextView>(R.id.txt_nota_geral_3).text = " jogatina até hoje!"
            }

            val tempoMedio = bundle.getString("tempoMedio")
            if (tempoMedio.isNullOrBlank()) {
                view.findViewById<TextView>(R.id.txt_nota_geral_4).text = ""
            }

            view.findViewById<TextView>(R.id.tempo_medio).text = tempoMedio
            view.findViewById<TextView>(R.id.nota_total_txt2).text = bundle.getString("notaTotal")
            view.findViewById<TextView>(R.id.jogatinas).text = jogatinas
            view.findViewById<TextView>(R.id.nota_total_txt).text = bundle.getString("notaTotal")
            view.findViewById<TextView>(R.id.nota_mecanica).text = bundle.getString("notaMecanica")
            view.findViewById<TextView>(R.id.nota_experiencia).text = bundle.getString("notaExperiencia")
            view.findViewById<TextView>(R.id.nota_componentes).text = bundle.getString("notaComponentes")

            super.onViewCreated(view, savedInstanceState)
        }
    }
}