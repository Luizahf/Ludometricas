package com.example.ludometricas.presentation.jogo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import kotlinx.android.synthetic.main.activity_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Date
import java.sql.Time
import kotlin.math.round

class JogoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)


        jogoViewModel.obterJogoSelecionado {
            setLayout(it!!)
        }

        btn_jogar.setOnClickListener {
            val intent = Intent(this, AvaliacaoActivity::class.java)
            startActivity(intent)
        }

    }

    fun setLayout(jogo: JogoLocal) {
        val dataRecorde = longToDate(jogo.RecordeData) ?: java.util.Date()
        val dataRecordeTxt = "${dataRecorde.day}/${dataRecorde.month}/${dataRecorde.year}"
        val tempoMedio = longToTime(jogo.tempoMedioJogatina)

        val tempoMedioTxt = if(tempoMedio != null) "${tempoMedio.hours}h ${tempoMedio.minutes}min" else ""

        findViewById<TextView>(R.id.titulo_jogo).text = jogo.nome
        findViewById<TextView>(R.id.nota_total_txt).text = jogo.notaMediaAteOMomento.round(2).toString()
        findViewById<TextView>(R.id.pontuacao_recorde_txt).text = jogo.RecordePontuacao.toString()
        findViewById<TextView>(R.id.responsavel_recorde_txt).text = jogo.RecordeResponsavel
        findViewById<TextView>(R.id.data_recorde_txt).text = dataRecordeTxt
        findViewById<TextView>(R.id.nota_mecanica_pl1).text = jogo.notaMecanicaMediaAteOMomento.toString()
        findViewById<TextView>(R.id.nota_componentes).text = jogo.notaComponentesMediaAteOMomento.toString()
        findViewById<TextView>(R.id.nota_experiencia).text = jogo.notaExperienciaMediaAteOMomento.toString()
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