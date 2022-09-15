package com.example.ludometricas.presentation.cronometro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.recorde.RecordeActivity
import com.example.ludometricas.presentation.util.CronometroState
import kotlinx.android.synthetic.main.activity_cronometro.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class CronometroActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    var cronometroState : CronometroState = CronometroState.stop
    var startTime : Long = 0
    var timeElapsed : Long = 0
    var rodarCronometro = false

    lateinit  var jogo : JogoLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)

        jogoViewModel.obterJogoSelecionado {
            if (it != null) {
                cronometro_titulo_jogo.text = it.nome
                jogo = it
            }
        }

        icn_play.setOnClickListener {
            if (cronometroState == CronometroState.stop) {
                cronometroState = CronometroState.play
                timeElapsed = 0
                startTime = System.currentTimeMillis()
                rodarCronometro = true
                inicarCronometro()
            } else if (cronometroState == CronometroState.pause) {
                cronometroState = CronometroState.play
                startTime = System.currentTimeMillis()
                rodarCronometro = true
                inicarCronometro()
            }
        }

        icn_stop.setOnClickListener {
            if (cronometroState == CronometroState.play) timeElapsed = System.currentTimeMillis() - startTime

            if (cronometroState != CronometroState.stop) {
                Log.e("TEMPO - ", exibirTempo(timeElapsed))
                rodarCronometro = false
                cronometroState = CronometroState.stop
            }
        }
        icn_pause.setOnClickListener {
            if (cronometroState == CronometroState.play) {
                timeElapsed += (System.currentTimeMillis() - startTime)
                rodarCronometro = false
                cronometroState = CronometroState.pause
            }
        }

        encerrarPartida.setOnClickListener {
            // update tempo da jogatina atual no jogo selecionado
            var seconds = (timeElapsed / 1000).toInt()
            jogo.tempoJogatina = seconds.toLong()
            jogo.jogatinas += 1
            jogo.tempoMedioJogatina = jogo.tempoJogatina / jogo.jogatinas
            jogoViewModel.updateJogo(jogo)

            val intent = Intent(this, RecordeActivity::class.java)
            startActivity(intent)
        }
    }

    fun inicarCronometro() {
        // Do work every 1s
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                if (rodarCronometro) {
                    exibirTempo(timeElapsed + (System.currentTimeMillis() - startTime))
                    handler.postDelayed(this, 1000)
                }
            }
        })
    }

    fun exibirTempo(elapsed: Long) : String {
        var seconds = floor((elapsed / 1000).toDouble())
        var minutes = floor((seconds / 60))
        var hours = floor((minutes / 60))

        seconds %= 60
        minutes %= 60
        hours %= 24
        val text = "${if (hours < 10) "0${hours.toInt()}" else "${hours.toInt()}"}:${if (minutes < 10) "0${minutes.toInt()}" else "${minutes.toInt()}"}:${if (seconds < 10) "0${seconds.toInt()}" else "${seconds.toInt()}"}"
        timer.text = text
        return text
    }
}