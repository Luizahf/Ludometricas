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
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.JogosAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Date
import java.sql.Time

class MainActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()
    private var jogos : List<Jogo> = mutableListOf()

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

        jogoViewModel.inserirJogos()
        // TODO jogos = jogoViewModel.obterJogos()!!
        recyclerView.adapter = JogosAdapter(jogos, ::clickListenerJogo, this)

    }

    fun clickListenerJogo(position: Int) {
        jogoViewModel.selecionarJogo(jogos[position])

        val intent = Intent(this, JogoActivity::class.java)
        intent.putExtra("JOGO_SELECIONADO", jogos[position].nome)
        startActivity(intent)
    }
}
