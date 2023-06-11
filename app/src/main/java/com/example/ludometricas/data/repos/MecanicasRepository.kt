package com.example.ludometricas.data.repos

import com.example.ludometricas.data.dao.MecanicasDao
import com.example.ludometricas.data.dataClasses.Mecanica
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
class MecanicasRepository(val dao: MecanicasDao) {
    fun insert(mecanicas: List<Mecanica>) {
        GlobalScope.launch {
            mecanicas.forEach {
                dao.insert(it)
            }
        }
    }

    fun insert(mecanica: Mecanica) {
        GlobalScope.launch {
            dao.insert(mecanica)
        }
    }

    fun get(nomeJogo: String, callback: (List<Mecanica>?) -> Any) {
        GlobalScope.launch {
            callback(dao.get(nomeJogo))
        }
    }
}