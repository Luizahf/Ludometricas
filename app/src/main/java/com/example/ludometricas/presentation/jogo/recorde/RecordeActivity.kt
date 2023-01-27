package com.example.ludometricas.presentation.jogo.recorde

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.MainActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_cronometro.*
import kotlinx.android.synthetic.main.activity_cronometro.cronometro_titulo_jogo
import kotlinx.android.synthetic.main.activity_jogo.*
import kotlinx.android.synthetic.main.activity_recorde.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class RecordeActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    lateinit  var jogo : JogoLocal

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorde)


        jogoViewModel.obterJogoSelecionado {
            if (it != null) {
                recorde_titulo_jogo.text = it.nome
                jogo = it

                responsavel_recorde_antigo.text = it.RecordeResponsavel
                nota_recorde_antigo.text = it.RecordePontuacao.toString()
            }
        }

        check_recorde.setOnClickListener {
            if (!responsavel_recorde.text.isNullOrBlank() && !nota_recorde.text.isNullOrBlank()) {
                jogo.RecordeResponsavel = responsavel_recorde.text.toString()
                jogo.RecordePontuacao = nota_recorde.text.toString().toInt()

                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
                jogo.RecordeData = current.format(formatter).toString()

                jogoViewModel.updateJogo(jogo)
            }

            startActivity(Intent(this, AvaliacaoActivity::class.java))
        }
    }
}