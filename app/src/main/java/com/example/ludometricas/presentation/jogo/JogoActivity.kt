package com.example.ludometricas.presentation.jogo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.data.Nota
import com.example.ludometricas.data.Recorde
import java.sql.Time

class JogoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)

        val obj: Jogo = intent.getSerializableExtra("JOGO_SELECIONADO") as Jogo
        findViewById<TextView>(R.id.titulo_jogo).setText(obj.nome)
    }
}