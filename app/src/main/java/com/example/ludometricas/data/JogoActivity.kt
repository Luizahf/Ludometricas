package com.example.ludometricas.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ludometricas.R

class JogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        val obj: Jogo = intent.getSerializableExtra("JOGO_SELECIONADO") as Jogo
        findViewById<TextView>(R.id.titulo_jogo).setText(obj.nome)
    }
}