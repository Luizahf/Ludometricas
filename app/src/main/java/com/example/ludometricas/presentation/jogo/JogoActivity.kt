package com.example.ludometricas.presentation.jogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.cronometro.CronometroActivity
import kotlinx.android.synthetic.main.activity_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class JogoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)


        jogoViewModel.obterJogoSelecionado {
            jogoSelecioando(it)
        }

        btn_jogar.setOnClickListener {
            val intent = Intent(this, CronometroActivity::class.java)
            startActivity(intent)
        }
    }

    fun jogoSelecioando(it: JogoLocal?) {
        if (it != null)
            setLayout(it)
    }

    override fun onBackPressed() {
        jogoViewModel.deselecionarJogo()
        super.onBackPressed()
    }

    fun setLayout(jogo: JogoLocal) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (jogo.jogatinas <= 0) {
            fragmentTransaction.add(R.id.primeiro_fragment, JogoNaoJogadoFragment())
        } else {
            if (jogo.RecordeResponsavel.isNotBlank()) {
                val recorde = RecordeFragment()
                val recordeBundle = Bundle()

                val recordeDate: String = (if (jogo.RecordeData != null) jogo.RecordeData!! else "")
                recordeBundle.putString("responsavel", jogo.RecordeResponsavel)
                recordeBundle.putString("pontuacao", jogo.RecordePontuacao.toString())
                recordeBundle.putString("data", recordeDate)
                recorde.setArguments(recordeBundle)
                fragmentTransaction.add(R.id.primeiro_fragment, recorde)
            }

            val historico = HistoricoJogoFragment()
            val historicoBundle = Bundle()
            val tempoMedioTxt = if (jogo.tempoMedioJogatina != null) "${(jogo.tempoMedioJogatina!!.toInt() / 3600)}h ${(jogo.tempoMedioJogatina!!.toInt() / 60)}min" else ""
            historicoBundle.putString("notaTotal", jogo.notaMediaAteOMomento.round(2).toString())
            historicoBundle.putString("notaMecanica", jogo.notaMecanicaMediaAteOMomento.round(2).toString())
            historicoBundle.putString("notaComponentes", jogo.notaComponentesMediaAteOMomento.round(2).toString())
            historicoBundle.putString("notaExperiencia", jogo.notaExperienciaMediaAteOMomento.round(2).toString())
            historicoBundle.putString("jogatinas", jogo.jogatinas.toString())
            historicoBundle.putString("tempoMedio", tempoMedioTxt)
            historico.setArguments(historicoBundle)
            val fragmentNum = if (jogo.RecordeResponsavel.isNotBlank()) R.id.segundo_fragment else R.id.primeiro_fragment
            fragmentTransaction.add(fragmentNum, historico)
        }

        //btn_ir_historico.visibility = if (jogo.jogatinas <= 0) INVISIBLE else VISIBLE
        fragmentTransaction.commit()
    }
    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}