package com.example.ludometricas.presentation.jogo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import com.example.ludometricas.R
import com.example.ludometricas.data.dataClasses.Mecanica
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_jogo.*
import kotlinx.android.synthetic.main.item_mecanicas_jogo_fragment.*
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class MecanicasFragment
    constructor(var vm: JogoViewModel)
: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_mecanicas_jogo_fragment, container, false)
    }

    @DelicateCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = this.arguments
        if (bundle != null) {
            val jogo = bundle.getString("jogo")

            vm.obterMecanicas(jogo!!, fun (mecanicas) {
                    view.findViewById<TextView>(R.id.txt_mecanicas).text = getMecanicasTxt(mecanicas)
            })

            popup_cadastrar_mecanica_button.setOnClickListener {
                vm.inserirMecanica(jogo, popup_cadastrar_mecanica_text.text.toString())
                popup_cadastrar_mecanica_background_container.visibility = View.GONE

                vm.obterMecanicas(jogo, fun (mecanicas) {
                    view.findViewById<TextView>(R.id.txt_mecanicas).text = getMecanicasTxt(mecanicas)
                })
            }
        }

        btn_add_mecanica.setOnClickListener {
            popup_cadastrar_mecanica_background_container.visibility = View.VISIBLE
        }

        popup_cancelar_cadastro_mecanica_button.setOnClickListener {
            popup_cadastrar_mecanica_background_container.visibility = View.GONE
        }
    }

    private fun getMecanicasTxt(mecanicas: List<Mecanica>?) : String {
        if (mecanicas.isNullOrEmpty()) {
            text_mecanica_bold.visibility = View.GONE

            val param = txt_mecanicas.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(8,16,8,16)
            txt_mecanicas.layoutParams = param

            return "Adicione as mecânicas do jogo para fazer uma avaliaçao mais completa."
        } else {
            text_mecanica_bold.visibility = View.VISIBLE

            val param = txt_mecanicas.layoutParams as ViewGroup.MarginLayoutParams
            param.setMargins(8,0,8,16)
            txt_mecanicas.layoutParams = param

            return mecanicas.joinToString { it.nome.lowercase(Locale.getDefault()) }
        }
    }
}