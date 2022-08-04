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
import com.example.ludometricas.data.Nota
import com.example.ludometricas.data.Recorde
import com.example.ludometricas.presentation.jogo.JogoActivity
import com.example.ludometricas.presentation.jogo.JogosAdapter
import java.sql.Date
import java.sql.Time

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

        recyclerView.adapter = JogosAdapter(criarJogos(), ::clickListenerJogo, this)

    }

    fun clickListenerJogo(jogo: Jogo) {
        val intent = Intent(this, JogoActivity::class.java)
        // intent.putExtra("JOGO_SELECIONADO", jogo) Todo mudar para view model
        startActivity(intent)
    }

    fun criarJogos() : MutableList<Jogo> {
        return mutableListOf(Jogo(
            "Lume",
            Recorde("Pepeu", 116, Date(11, 5, 22)),
            Time(0, 50, 0),
            Nota(8.795, 0.0, 0.0, 0.0),
            jogatinas = 4
        ), Jogo(
            "Viticulture + Turcany",
            Recorde("Pepeu", 44, Date(9, 3, 22)),
            Time(1, 25, 0),
            Nota(9.15, 0.0, 0.0, 0.0),
            jogatinas = 1
        ))
    }
}
