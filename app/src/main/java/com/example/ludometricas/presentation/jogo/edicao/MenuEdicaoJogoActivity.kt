package com.example.ludometricas.presentation.jogo.edicao

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.ludometricas.R
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.jogo.JogoActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.recorde.RecordeActivity
import kotlinx.android.synthetic.main.activity_menu_edicao_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MenuEdicaoJogoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_edicao_jogo)

        btn_add_jogatina_manual.setOnClickListener {
            desfoque_menu_edicao.visibility = View.VISIBLE
            popup_jogatina_manual_background_container.visibility = View.VISIBLE
        }


        jogoViewModel.obterJogoSelecionado {
            if (it != null) {
                titulo_jogo.text = it.nome
            }
        }

        popup_jogatina_manual_button.setOnClickListener {
            if (popup_jogatina_manual_text.text.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                try {
                    val dt = LocalDate.parse(popup_jogatina_manual_text.text.toString(), formatter)
                    if (dt.isAfter(LocalDate.now())) {
                        hideKeyboard(currentFocus ?: View(this))
                        Toast.makeText(this, "A data deve ser inferior a atual", Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, RecordeActivity::class.java)
                        intent.putExtra("dataJogatinaAntiga", popup_jogatina_manual_text.text.toString())
                        startActivity(intent)
                    }
                }
                catch (e: Exception) {
                    hideKeyboard(currentFocus ?: View(this))
                    Toast.makeText(this, "Por favor insira a data no formato: dd/mm/yyyy", Toast.LENGTH_SHORT).show()
                }
            } else {
                desfoque_menu_edicao.visibility = View.GONE
                popup_jogatina_manual_background_container.visibility = View.GONE
                hideKeyboard(currentFocus ?: View(this))
            }
        }

        desfoque_menu_edicao.setOnClickListener {
            desfoque_menu_edicao.visibility = View.GONE
            popup_jogatina_manual_background_container.visibility = View.GONE
            popup_editar_nome_jogo_background_container.visibility = View.GONE
            popup_deletar_jogo.visibility = View.GONE
            hideKeyboard(currentFocus ?: View(this))
        }

        btn_editar.setOnClickListener {
            popup_editar_nome_jogo_background_container.visibility = View.VISIBLE
            desfoque_menu_edicao.visibility = View.VISIBLE
        }

        popup_editar_nome_jogo_button.setOnClickListener {
            if (!popup_editar_nome_jogo_text.text.isNullOrEmpty()) {
                jogoViewModel.obterJogoSelecionado{
                    titulo_jogo.text = popup_editar_nome_jogo_text.text.toString()
                    jogoViewModel.updateNome(it!!, popup_editar_nome_jogo_text.text.toString())
                }
            }
            desfoque_menu_edicao.visibility = View.GONE
            popup_editar_nome_jogo_background_container.visibility = View.GONE
        }
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onBackPressed() {
        finish()
    }
}