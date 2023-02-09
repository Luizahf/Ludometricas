package com.example.ludometricas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ludometricas.data.dataClasses.JogatinaLocal

@Dao
interface JogatinasDao {
    @Query("SELECT * FROM jogatinas")
    fun get(): JogatinaLocal?

    @Insert
    fun insert(jotatina: JogatinaLocal)

    @Query("DELETE FROM jogatinas")
    fun deleteAll()
}