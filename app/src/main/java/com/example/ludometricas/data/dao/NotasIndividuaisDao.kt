package com.example.ludometricas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ludometricas.data.dataClasses.NotaIndividualLocal

@Dao
interface NotasIndividuaisDao {
    @Query("SELECT * FROM notasIndividuais")
    fun get(): List<NotaIndividualLocal>?

    @Insert
    fun insert(nota: NotaIndividualLocal)

    @Query("DELETE FROM notasIndividuais")
    fun deleteAll()
}