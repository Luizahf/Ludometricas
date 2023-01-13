package com.example.ludometricas.presentation.jogo.metricas.historico

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidplot.xy.*
import com.example.ludometricas.R
import kotlinx.android.synthetic.main.activity_historico_metricas.*
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*

class HistoricoMetricasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historico_metricas)


        var series = arrayOf<Number>(8, 8.2, 8.12, 9, 9.2, 10, 7.19)
        var domain = arrayOf<Number>(2, 7, 10, 20, 30)

        val series1 : XYSeries = SimpleXYSeries(Arrays.asList(* series), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series")
        val series1format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)
        historico_plot.addSeries(series1, series1format)
        historico_plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = object : Format() {
            override fun format(obj: Any?, toAppendTo: StringBuffer, pos: FieldPosition): StringBuffer {
                val i = Math.round((obj as Number).toFloat())
                return toAppendTo.append(domain[i])
            }

            override fun parseObject(p0: String?, p1: ParsePosition?): Any? {
                return null
            }
        }
        PanZoom.attach(historico_plot)
    }
}