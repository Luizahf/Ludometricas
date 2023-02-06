package com.example.ludometricas.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ExpandableListView
import android.widget.TextView
import com.example.ludometricas.R
import kotlinx.android.synthetic.main.activity_main.*

class ExpandableListViewAdapter internal constructor (private val context: Context, private val listSort: List<String>, private val itensSort: HashMap<String, List<String>>, private val lista: ExpandableListView, private val callback: (String) -> Any) :
    BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return listSort.size
    }

    override fun getChildrenCount(p0: Int): Int {
        return itensSort[listSort[p0]]!!.size
    }

    override fun getGroup(p0: Int): Any {
        return listSort[p0]
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return itensSort[listSort[p0]]!![p1]
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        var convertView = p2
        val title = getGroup(p0) as String

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.lista_sort_jogos, null)
        }

        convertView!!.findViewById<TextView>(R.id.lista_sort).setText(title)
        return convertView
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        var convertView = p3
        val title = getChild(p0, p1) as String

        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.itens_sort_jogos, null)
        }

        convertView!!.findViewById<TextView>(R.id.opcoes).setText(title)
        convertView!!.findViewById<TextView>(R.id.opcoes).setOnClickListener {
            callback(title)
            lista.collapseGroup(p0)
        }
        return convertView
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}