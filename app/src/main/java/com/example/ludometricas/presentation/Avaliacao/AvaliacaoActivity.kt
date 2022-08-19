package com.example.ludometricas.presentation.Avaliacao

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.ludometricas.R
import com.example.ludometricas.data.Avaliacao
import com.example.ludometricas.data.Nota
import com.example.ludometricas.data.NotaIndividual
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.MainActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_avaliacao.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class AvaliacaoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()
    lateinit var jogoAtual: JogoLocal
    var notaMecanicaPl1 = 0.0
    var notaMecanicaPl2 = 0.0
    var notaComponentesPl1 = 0.0
    var notaComponentesPl2 = 0.0
    var notaExperienciaPl1 = 0.0
    var notaExperienciaPl2 = 0.0
    var notaTotalPl1 = 0.0
    var notaTotalPl2 = 0.0
    var notaSomadaAteOmomento = 0.0
    var jogatinas = 0
    var notaTotalJogatina = 0.0
    var notaTotalMediaAteOMomento = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_avaliacao)


        jogoViewModel.obterJogoSelecionado {
            jogoAtual = it!!
            setLayout(it!!)
        }
        listenersMecanica()
        listenersComponentes()
        listenersExperiencia()

        icn_salval_avaliacao.setOnClickListener {
            var individuais = mutableListOf<NotaIndividual>()
            individuais.add(NotaIndividual("Lulu", Nota(notaTotalPl1, notaMecanicaPl1, notaComponentesPl1, notaExperienciaPl1), Date()))
            individuais.add(NotaIndividual("Pepeu", Nota(notaTotalPl2, notaMecanicaPl2, notaComponentesPl2, notaExperienciaPl2), Date()))
            val a = Avaliacao(
                jogoAtual.nome, notaTotalMediaAteOMomento, notaTotalJogatina, (notaMecanicaPl1+notaMecanicaPl2)/2, (notaComponentesPl1+notaComponentesPl2)/2, (notaExperienciaPl1+notaExperienciaPl1)/2, individuais
            )
            jogoViewModel.avaliar(a)

            // atualizar banco

            val toast = Toast.makeText(applicationContext, "Avaliaçao salva com sucesso!", Toast.LENGTH_SHORT)
            toast.show()

            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun listenersMecanica() {
        nota_mecanica_pl1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaMecanicaPl1 = s.toString().toDouble()
                    calcularNotaTotalPl1()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        nota_mecanica_pl2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaMecanicaPl2 = s.toString().toDouble()
                    calcularNotaTotalPl2()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    private fun listenersComponentes() {
        nota_componentes_pl1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaComponentesPl1 = s.toString().toDouble()
                    calcularNotaTotalPl1()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        nota_coomponentes_pl2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaComponentesPl2 = s.toString().toDouble()
                    calcularNotaTotalPl2()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
    private fun listenersExperiencia() {
        nota_experiencia_pl1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaExperienciaPl1 = s.toString().toDouble()
                    calcularNotaTotalPl1()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })

        nota_experiencia_pl2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if(s.isNotEmpty()) {
                    notaExperienciaPl2 = s.toString().toDouble()
                    calcularNotaTotalPl2()
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun calcularNotaTotalPl1() {
        if (notaMecanicaPl1 > 0 && notaComponentesPl1 > 0 && notaExperienciaPl1 > 0) {
            notaTotalPl1 = calcularNotaTotal(notaMecanicaPl1, notaComponentesPl1, notaExperienciaPl1)
            nota_pl1.text = notaTotalPl1.round(2).toString()
            if (notaTotalPl2 > 0) {
                notaTotalJogatina = (notaTotalPl1 + notaTotalPl2) / 2
                notaTotalMediaAteOMomento = ((notaSomadaAteOmomento + notaTotalJogatina) / (jogatinas+1)).round(2)
                nota_total_avaliacao_txt.text =notaTotalMediaAteOMomento.toString()
                nota_jogatina.text = ((notaTotalPl1 + notaTotalPl2) / 2).toString()
            }
        }
    }
    private fun calcularNotaTotalPl2() {
        if (notaMecanicaPl2 > 0 && notaComponentesPl2 > 0 && notaExperienciaPl2 > 0) {
            notaTotalPl2 =  calcularNotaTotal(notaMecanicaPl2, notaComponentesPl2, notaExperienciaPl2)
            nota_pl2.text = notaTotalPl2.round(2).toString()
            if (notaTotalPl1 > 0) {
                notaTotalMediaAteOMomento = ((notaSomadaAteOmomento + (notaTotalPl1 + notaTotalPl2)) / (jogatinas+1)).round(2)
                nota_total_avaliacao_txt.text =notaTotalMediaAteOMomento.toString()
                nota_jogatina.text = ((notaTotalPl1 + notaTotalPl2) / 2).toString()
            }
        }
    }

    private fun calcularNotaTotal(mec: Double, comp: Double, exp: Double): Double {
        return (mec*0.3)+(comp*0.2)+(exp*0.5)
    }

    private fun setLayout(jogo: JogoLocal) {
        avaliacao_titulo_jogo.text = jogo.nome
        nota_total_avaliacao_txt.text = jogo.notaMediaAteOMomento.round(2).toString()
        nota_jogatina.text = "0.0"
        nota_pl1.text = "0.0"
        nota_pl2.text = "0.0"
        notaTotalJogatina = jogo.notaMediaAteOMomento
        jogatinas = jogo.jogatinas
        notaSomadaAteOmomento = jogo.notaTotalAteOMomento
    }
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}