package com.example.ludometricas.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogatina
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
    private lateinit var listViewAdapter: ExpandableListViewAdapter
    private lateinit var listaSortJogos : List<String>
    private lateinit var listaOpcoes : HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showList()
        listViewAdapter = ExpandableListViewAdapter(this, listaSortJogos, listaOpcoes, lista_sort_jogos, ::sortJogos)
        lista_sort_jogos.setAdapter(listViewAdapter)

        jogoViewModel.obterJogos {
            jogos = it.sortedByDescending { it.notaMediaAteOMomento.total }
            atualizarLista()
        }

        jogoViewModel.jogos.observe(this, Observer {
            jogos = it.sortedByDescending { it.notaMediaAteOMomento.total }
            atualizarLista()
        })

        btn_add_game.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    fun sortJogos(sort : String) {
        if (sort == "Nota maior-menor") jogos = jogos.sortedByDescending { it.notaMediaAteOMomento.total }
        if (sort == "Nota menor-maior") jogos = jogos.sortedBy { it.notaMediaAteOMomento.total }
        if (sort == "Alfabética") jogos = jogos.sortedBy { it.nome }
        if (sort == "N. jogatinas menor-maior") jogos = jogos.sortedBy { it.jogatinas }
        if (sort == "N. jogatinas maior-menor") jogos = jogos.sortedByDescending { it.jogatinas }
        if (sort == "Jogatina mais recente") jogos = jogos.sortedByDescending {  if(it.historicoJogatinas.isNullOrEmpty()) "0" else it.historicoJogatinas.sortedBy { it.data }.last().data!! }
        if (sort == "Jogatina mais antiga") jogos = jogos.sortedBy { if(it.historicoJogatinas.isNullOrEmpty()) "0" else it.historicoJogatinas.sortedBy { it.data }.last().data!! }

        atualizarLista()
    }

    private fun showList() {
        listaSortJogos = ArrayList()
        listaOpcoes = HashMap()

        (listaSortJogos as ArrayList<String>).add("Ordenar")
        val opcoes : MutableList<String> = ArrayList()
        opcoes.add("Nota maior-menor")
        opcoes.add("Nota menor-maior")
        opcoes.add("Alfabética")
        opcoes.add("Jogatina mais recente")
        opcoes.add("Jogatina mais antiga")
        opcoes.add("N. jogatinas menor-maior")
        opcoes.add("N. jogatinas maior-menor")

        listaOpcoes[listaSortJogos[0]] = opcoes
    }

    private fun atualizarLista() {
        val recyclerView = findViewById<RecyclerView>(R.id.lista_jogos)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

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
            atualizarLista()
        }

        super.onRestart()
    }
}
