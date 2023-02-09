package com.example.ludometricas.presentation.jogo.historico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogatina
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.JogosAdapter
import kotlinx.android.synthetic.main.activity_historico_jogatinas.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoricoJogatinasActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_jogatinas)

        jogoViewModel.obterJogoSelecionado{
            it!!.nome.also { titulo_jogo.text = it }
        }

        jogoViewModel.obtecrHistoricoJogatinas {
            val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_hiostorico)
            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = layoutManager
            HistoricoAdapter(it, ::clickListenerHistorico, this).also { recyclerView.adapter = it }
        }
    }

    private fun clickListenerHistorico(jogatina: Jogatina) {

    }
}