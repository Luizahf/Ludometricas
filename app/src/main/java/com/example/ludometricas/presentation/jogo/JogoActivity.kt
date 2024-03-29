package com.example.ludometricas.presentation.jogo

import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.cronometro.CronometroActivity
import com.example.ludometricas.presentation.jogo.edicao.MenuEdicaoJogoActivity
import com.example.ludometricas.jogatina.historico.HistoricoJogatinasActivity
import com.example.ludometricas.presentation.jogo.fragments.HistoricoGraficoJogoFragment
import com.example.ludometricas.presentation.jogo.fragments.JogoNaoJogadoFragment
import com.example.ludometricas.presentation.jogo.fragments.MecanicasFragment
import com.example.ludometricas.presentation.jogo.fragments.RecordeFragment
import kotlinx.android.synthetic.main.activity_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

        icn_editar_jogo.setOnClickListener {
            val intent = Intent(this, MenuEdicaoJogoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        jogoViewModel.obterJogoSelecionado {
            jogoSelecioando(it)
        }
        super.onResume()
    }

    fun jogoSelecioando(it: JogoLocal?) {
        if (it != null)
            setLayout(it)
    }

    override fun onBackPressed() {
        jogoViewModel.deselecionarJogo()
        finish()
        super.onBackPressed()
    }

    fun setLayout(jogo: JogoLocal) {
        titulo_jogo.text = jogo.nome

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

            val historico = HistoricoGraficoJogoFragment()
            val historicoBundle = Bundle()

            val minutosJogados = jogo.tempoMedioJogatina.toInt() / 60
            val tempoMedioTxt = if (minutosJogados > 0) {
                val horas = jogo.tempoMedioJogatina.toInt() / 3600
                if (horas > 0) {
                    "${horas}h ${minutosJogados-(60*horas)}min"
                } else {
                    "${minutosJogados}min"
                }
            } else ""

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

        // Exibindo fragment de mecânicas do jogo
        val fragmentMecanicas = MecanicasFragment(jogoViewModel)
        val mecanicasBundle = Bundle()

        val mecanicasTxt = "Mecânicas: \${}"
        mecanicasBundle.putString("mecanicas", mecanicasTxt)
        mecanicasBundle.putString("jogo", jogo.nome)
        fragmentMecanicas.arguments = mecanicasBundle
        fragmentTransaction.add(R.id.terceiro_fragment, fragmentMecanicas)


        // Exibindo btn de histórico de jogatinas
        btn_ir_historico.visibility = if (jogo.jogatinas <= 0) INVISIBLE else VISIBLE
        btn_ir_historico.setOnClickListener {
            startActivity(Intent(this, HistoricoJogatinasActivity::class.java))
        }
        fragmentTransaction.commit()
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return kotlin.math.round(this * multiplier) / multiplier
    }
}