package com.example.ludometricas.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.presentation.cadastro.CadastroActivity
import com.example.ludometricas.presentation.cronometro.CronometroActivity
import com.example.ludometricas.presentation.jogo.JogoActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.JogosAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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


        //jogoViewModel.inserirJogos()
        jogoViewModel.obterJogos {
            atualizarLista(it)
        }

        btn_add_game.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun atualizarLista(it: List<Jogo>) {
        val recyclerView = findViewById<RecyclerView>(R.id.lista_jogos)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        jogos = it.sortedByDescending { it.notaMediaAteOMomento.total }
        recyclerView.adapter = JogosAdapter(jogos, ::clickListenerJogo, this)
    }

    fun clickListenerJogo(position: Int) {
        if (!jogos.isNullOrEmpty()) {
            jogoViewModel.selecionarJogo(jogos[position]) {
                val intent = Intent(this, JogoActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onRestart() {
        jogoViewModel.obterJogos {
            atualizarLista(it)
        }

        super.onRestart()
    }
}
