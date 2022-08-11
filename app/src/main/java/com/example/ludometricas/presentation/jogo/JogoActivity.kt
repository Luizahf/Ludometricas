package com.example.ludometricas.presentation.jogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ludometricas.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class JogoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)


        jogoViewModel.obterJogoSelecionado {
            findViewById<TextView>(R.id.titulo_jogo).setText(it!!.nome)
        }
    }
}