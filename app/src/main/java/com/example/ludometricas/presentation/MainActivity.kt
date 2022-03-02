package com.example.ludometricas.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.presentation.jogo.JogoActivity
import com.example.ludometricas.presentation.jogo.JogosAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.lista_jogos)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        val jogo1 = Jogo()
        jogo1.nome = "7 Wonders"
        jogo1.nota = 9.0
        jogo1.pontuacaMedia = 120.0
        jogo1.recorde = 215
        jogo1.tempoMedio = 45
        val jogo2 = Jogo()
        jogo2.nome = "Agrícola"
        jogo2.nota = 8.6
        jogo2.pontuacaMedia = 133.0
        jogo2.recorde = 196
        jogo2.tempoMedio = 94

        recyclerView.adapter = JogosAdapter(listOf(jogo1, jogo2), ::clickListenerJogo, this)

    }

    fun clickListenerJogo(jogo: Jogo) {
        val intent = Intent(this, JogoActivity::class.java)
        intent.putExtra("JOGO_SELECIONADO", jogo)
        startActivity(intent)
    }
}
