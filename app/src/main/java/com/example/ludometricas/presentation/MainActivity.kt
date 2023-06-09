package com.example.ludometricas.presentation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.presentation.jogo.JogoActivity
import com.example.ludometricas.presentation.jogo.JogoViewModel
import com.example.ludometricas.presentation.jogo.JogosAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_menu_edicao_jogo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()
    private var jogos : List<Jogo> = mutableListOf()
    private var jogosSemFiltro : List<Jogo> = mutableListOf()
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

       showList("Ordenar")
        listViewAdapter = ExpandableListViewAdapter(this, listaSortJogos, listaOpcoes, lista_sort_jogos, ::sortJogos)
        lista_sort_jogos.setAdapter(listViewAdapter)

        jogoViewModel.obterJogos {
            jogos = it.sortedByDescending { it.notaMediaAteOMomento.total }
            for (i in 1..jogos.count()) {
                jogos[i-1].posicaoNota = i
            }

            jogosSemFiltro = jogos
            atualizarLista()
        }

        jogoViewModel.jogos.observe(this, Observer {
            jogos = it.sortedByDescending { it.notaMediaAteOMomento.total }
            for (i in 1..jogos.count()) {
                jogos[i-1].posicaoNota = i
            }
            jogosSemFiltro = jogos
            atualizarLista()
        })

        btn_add_game.setOnClickListener {
            popup_cadastrar_jogo.visibility = View.VISIBLE
            desfoque_cadastrar_jogo.visibility = View.VISIBLE

        }

        popup_cadastrar_jogo_button.setOnClickListener {
            val newGameName = popup_cadastrar_jogo_text.text
            if (!newGameName.toString().isNullOrBlank()) {
                jogoViewModel.inserirNovoJogo(newGameName.toString()) { inserido ->
                    if (inserido) {
                        jogoViewModel.obterJogos {
                            popup_cadastrar_jogo.visibility = View.GONE
                            desfoque_cadastrar_jogo.visibility = View.GONE
                            sortJogos("Menos jogados")
                            atualizarLista()
                        }
                    } else {
                        Toast.makeText(this, "Esse jogo já existe", Toast.LENGTH_LONG).show()
                    }
                    hideKeyboard(currentFocus ?: View(this))
                }
            } else {
                popup_cadastrar_jogo.visibility = View.GONE
                desfoque_cadastrar_jogo.visibility = View.GONE
            }
        }

        desfoque_cadastrar_jogo.setOnClickListener {
            popup_cadastrar_jogo.visibility = View.GONE
            desfoque_cadastrar_jogo.visibility = View.GONE
        }

        bucs_jogo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty()) {
                    jogos = jogosSemFiltro.filter { it.nome.lowercase(Locale.getDefault()).startsWith(p0.toString().lowercase(Locale.getDefault()))}
                    atualizarLista("Alfabética")
                } else {
                    jogos = jogosSemFiltro
                    atualizarLista("Alfabética")
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }
    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun sortJogos(sort : String) {
        if (sort == "Maiores notas") jogos = jogos.sortedByDescending { it.notaMediaAteOMomento.total }
        else if (sort == "Menores notas") jogos = jogos.sortedBy { it.notaMediaAteOMomento.total }
        else if (sort == "Alfabética") jogos = jogos.sortedBy { it.nome }
        else if (sort == "Menos jogados") jogos = jogos.sortedBy { it.jogatinas }
        else if (sort == "Mais jogados") jogos = jogos.sortedByDescending { it.jogatinas }
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        if (sort == "Jogados recentemente") jogos = jogos.sortedByDescending {  LocalDate.parse(if(it.historicoJogatinas.isNullOrEmpty()) "01/01/0001" else it.historicoJogatinas.sortedBy { LocalDate.parse(it.data, formatter) }.last().data!!, formatter) }

        atualizarLista(sort)
    }

    private fun showList(name: String) {
        listaSortJogos = ArrayList()
        listaOpcoes = HashMap()

        (listaSortJogos as ArrayList<String>).add(name)
        val opcoes : MutableList<String> = ArrayList()
        opcoes.add("Maiores notas")
        opcoes.add("Menores notas")
        opcoes.add("Alfabética")
        opcoes.add("Menos jogados")
        opcoes.add("Mais jogados")
        opcoes.add("Jogados recentemente")

        listaOpcoes[listaSortJogos[0]] = opcoes
    }

    private fun atualizarLista(sort : String? = "Ordenar") {
        val recyclerView = findViewById<RecyclerView>(R.id.lista_jogos)
        val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = JogosAdapter(jogos, ::clickListenerJogo, this)

        if (sort != null) {
            showList(sort)
            listViewAdapter = ExpandableListViewAdapter(this, listaSortJogos, listaOpcoes, lista_sort_jogos, ::sortJogos)
            lista_sort_jogos.setAdapter(listViewAdapter)
        }
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
