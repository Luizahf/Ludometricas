package com.example.ludometricas.presentation.jogo.recorde

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_recorde.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class RecordeActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    lateinit  var jogo : JogoLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorde)
        val date = intent.getStringExtra("dataJogatinaAntiga")

        jogoViewModel.obterJogoSelecionado {
            if (it != null) {
                recorde_titulo_jogo.text = it.nome
                jogo = it

                if (!it.RecordeResponsavel.isNullOrBlank()) {
                    responsavel_recorde_antigo.text = it.RecordeResponsavel
                    nota_recorde_antigo.text = if (it.RecordePontuacao.toString() == "0") "" else it.RecordePontuacao.toString()
                    pontuacao_recorde.text = nota_recorde_antigo.text
                    data_recorde.text = it.RecordeData
                    if (!date.isNullOrBlank()) jogo.tempoJogatina = 0
                } else {
                    caixa_nota_geral.visibility = View.GONE
                }
            }
        }

        check_recorde.setOnClickListener {
            try {
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                val dataJogatina : String = if (date.isNullOrBlank()) LocalDate.now().format(formatter) else date.format(formatter)
                if (!responsavel_recorde.text.isNullOrBlank() && !nota_recorde.text.isNullOrBlank()) {
                    if (jogo.RecordePontuacao >= nota_recorde.text.toString().toInt()) {
                        hideKeyboard(currentFocus ?: View(this))
                        Toast.makeText(this, "O recorde atual é de: ${jogo.RecordePontuacao}. Portanto nao houve recorde dessa vez :(", Toast.LENGTH_LONG).show()
                    } else {
                        jogo.RecordeResponsavel = responsavel_recorde.text.toString()
                        jogo.RecordePontuacao = nota_recorde.text.toString().toInt()

                        jogo.RecordeData = dataJogatina.format(formatter)

                        jogoViewModel.updateJogo(jogo)
                        irParaAvaliacao(dataJogatina)
                    }
                } else {
                    if (responsavel_recorde.text.isNullOrBlank() && !nota_recorde.text.isNullOrBlank() || !responsavel_recorde.text.isNullOrBlank() && nota_recorde.text.isNullOrBlank()) {
                        hideKeyboard(currentFocus ?: View(this))
                        Toast.makeText(this, "Há alguma informacao faltante", Toast.LENGTH_LONG).show()
                    } else {
                        irParaAvaliacao(dataJogatina)
                    }
                }
            } catch (e: Exception) {
                hideKeyboard(currentFocus ?: View(this))
                Toast.makeText(this, "Nao foi possivel registrar. Por favor confira a nota inserida", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun irParaAvaliacao(dataRecorde: String) {
        val intent = Intent(this, AvaliacaoActivity::class.java)
        intent.putExtra("dataJogatinaAntiga", dataRecorde)
        startActivity(intent)
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}