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
            view.findViewById<TextView>(R.id.nota_total_txt).text = bundle.getString("notaTotal")
            view.findViewById<TextView>(R.id.nota_mecanica).text = bundle.getString("notaMecanica")
            view.findViewById<TextView>(R.id.nota_experiencia).text = bundle.getString("notaExperiencia")
            view.findViewById<TextView>(R.id.nota_componentes).text = bundle.getString("notaComponentes")
            view.findViewById<TextView>(R.id.jogatinas).text = bundle.getString("jogatinas")
            view.findViewById<TextView>(R.id.tempo_medio).text = bundle.getString("tempoMedio")

            super.onViewCreated(view, savedInstanceState)
        }
    }
}