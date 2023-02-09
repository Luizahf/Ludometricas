package com.example.ludometricas.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ludometricas.data.NotaIndividual

@Entity(tableName = "jogatinas")
class JogatinaLocal (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var data: String? = null,
    var duracao: String = "0",
    var duracaoPreparacao: String = "0"
)