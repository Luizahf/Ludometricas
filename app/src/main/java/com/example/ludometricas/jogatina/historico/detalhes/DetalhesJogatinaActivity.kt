package com.example.ludometricas.jogatina.historico.detalhes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.jogatina.JogatinaViewModel
import com.example.ludometricas.presentation.jogo.JogoViewModel
import kotlinx.android.synthetic.main.activity_detalhes_jogatina.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetalhesJogatinaActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()
    private val viewModel: JogatinaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_jogatina)

        jogoViewModel.obterJogoSelecionado {
            if (it != null) {
                titulo_jogo_detalhes_jogatina.text = it.nome
            }
        }

        viewModel.obterJogatinaSelecionada {
            if (it != null) {
                data_jogatina.text = it.data
                val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_notas_individuais)
                val layoutManager =
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                recyclerView.layoutManager = layoutManager

                viewModel.obterNotasIndividuais(it) {
                    if (it!= null){
                        recyclerView.adapter = NotasIndividuaisAdapter(it, this)
                    }
                }
            }
        }
    }
}