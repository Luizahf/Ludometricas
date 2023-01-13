package com.example.ludometricas.presentation.jogo

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.cronometro.CronometroActivity
import com.example.ludometricas.presentation.jogo.metricas.historico.HistoricoMetricasActivity
import kotlinx.android.synthetic.main.activity_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.round

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

        btn_ir_historico.setOnClickListener {
            val intent = Intent(this, HistoricoMetricasActivity::class.java)
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
        val tempoMedioTxt = if(jogo.tempoMedioJogatina != null) "${(jogo.tempoMedioJogatina!!.toInt()/3600)}h ${(jogo.tempoMedioJogatina!!.toInt()/60)}min" else ""

        findViewById<TextView>(R.id.titulo_jogo).text = jogo.nome
        findViewById<TextView>(R.id.nota_total_txt).text = jogo.notaMediaAteOMomento.round(2).toString()
        findViewById<TextView>(R.id.pontuacao_recorde_txt).text = jogo.RecordePontuacao.toString()
        findViewById<TextView>(R.id.responsavel_recorde_txt).text = jogo.RecordeResponsavel
        val recordeDate : String = (if (jogo.RecordeData != null) SimpleDateFormat("dd/MM/yyyy").format(SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(jogo.RecordeData)) else "")
        findViewById<TextView>(R.id.data_recorde_txt).text = recordeDate
        findViewById<TextView>(R.id.data_recorde_txt).visibility = if (jogo.RecordeData != null) View.VISIBLE else View.INVISIBLE
        findViewById<TextView>(R.id.nota_mecanica_pl1).text = jogo.notaMecanicaMediaAteOMomento.round(2).toString()
        findViewById<TextView>(R.id.nota_componentes).text = jogo.notaComponentesMediaAteOMomento.round(2).toString()
        findViewById<TextView>(R.id.nota_experiencia).text = jogo.notaExperienciaMediaAteOMomento.round(2).toString()
        findViewById<TextView>(R.id.jogatinas).text = jogo.jogatinas.toString()
        findViewById<TextView>(R.id.tempo_medio).text = tempoMedioTxt
    }
    fun longToDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }
    fun longToTime(dateLong: Long?): Time? {
        return dateLong?.let { Time(it) }
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}