package com.example.ludometricas.presentation.cadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ludometricas.R
import com.example.ludometricas.presentation.MainActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_cadastro.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CadastroActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_cadastro)

        background_top.setOnClickListener {
            finish()
        }
        background_bottom.setOnClickListener {
            finish()
        }

        popup_window_button.setOnClickListener {
            val newGameName = popup_window_text.text
            if (!newGameName.toString().isNullOrBlank()) {
                jogoViewModel.inserirNovoJogo(newGameName.toString())
                finish()
            } else {
                finish()
            }
        }
    }
}