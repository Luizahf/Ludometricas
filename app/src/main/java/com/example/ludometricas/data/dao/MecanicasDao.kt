package com.example.ludometricas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ludometricas.data.dataClasses.Mecanica

@Dao
interface MecanicasDao {
    @Query("SELECT * FROM mecanicas WHERE nomeJogo = :nomeJogo")
    fun get(nomeJogo: String): List<Mecanica>?

    @Insert
    fun insert(mecanica: Mecanica)

    @Query("UPDATE mecanicas SET nome = :nomeNovo WHERE nomeJogo = :nomeJogo AND nome = :nomeAntigo")
    fun update(nomeJogo: String, nomeAntigo: String, nomeNovo: String)
}