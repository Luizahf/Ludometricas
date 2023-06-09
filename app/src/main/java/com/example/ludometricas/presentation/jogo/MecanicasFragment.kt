package com.example.ludometricas.presentation.jogo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import com.example.ludometricas.R
import kotlinx.android.synthetic.main.activity_jogo.*
import kotlinx.android.synthetic.main.item_mecanicas_jogo_fragment.*

class MecanicasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_mecanicas_jogo_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        if (bundle != null) {
            view.findViewById<TextView>(R.id.txt_mecanicas).text = bundle.getString("mecanicas")
        }

        btn_add_mecanica.setOnClickListener {
            popup_cadastrar_mecanica_background_container.visibility = View.VISIBLE
        }

        popup_cancelar_cadastro_mecanica_button.setOnClickListener {
            popup_cadastrar_mecanica_background_container.visibility = View.GONE
        }
    }
}