package com.example.ludometricas.presentation.jogo.fragments

import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ludometricas.R
import kotlinx.android.synthetic.main.caixa_recorde.*
import android.R.attr.defaultValue







class RecordeFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.caixa_recorde, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        if (bundle != null) {
            view.findViewById<TextView>(R.id.responsavel_recorde_txt).text = bundle.getString("responsavel")
            view.findViewById<TextView>(R.id.data_recorde_txt).text = bundle.getString("data")
            view.findViewById<TextView>(R.id.pontuacao_recorde_txt).text = bundle.getString("pontuacao")
            super.onViewCreated(view, savedInstanceState)
        }
    }
}
