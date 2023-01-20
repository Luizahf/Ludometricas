package com.example.ludometricas.presentation.jogo

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.ludometricas.R
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.presentation.Avaliacao.AvaliacaoActivity
import com.example.ludometricas.presentation.cronometro.CronometroActivity
import com.example.ludometricas.presentation.jogo.metricas.historico.HistoricoMetricasActivity
import kotlinx.android.synthetic.main.activity_jogo.*
import kotlinx.android.synthetic.main.historico_jogo.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.sql.Date
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.round

class JogoActivity : AppCompatActivity() {
    private val jogoViewModel: JogoViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jogo)


        jogoViewModel.obterJogoSelecionado {
            jogoSelecioando(it)
        }

        btn_jogar.setOnClickListener {
            val intent = Intent(this, CronometroActivity::class.java)
            startActivity(intent)
        }
    }

    fun jogoSelecioando(it: JogoLocal?) {
        if (it != null)
            setLayout(it)
    }

    override fun onBackPressed() {
        jogoViewModel.deselecionarJogo()
        super.onBackPressed()
    }

    fun setLayout(jogo: JogoLocal) {
        GlobalScope.launch {
            val recyclerView = findViewById<RecyclerView>(R.id.caixar_jogo_historico_view)
            val layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = HistoricoAdapter(jogo, fun() {
                val intent = Intent(this@JogoActivity, HistoricoMetricasActivity::class.java)
                startActivity(intent)
            }, this@JogoActivity)
        }
    }
}