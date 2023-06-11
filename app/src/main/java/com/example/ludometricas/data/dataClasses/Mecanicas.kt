package com.example.ludometricas.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="mecanicas")
data class Mecanica (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var nome: String = "",
    var nomeJogo: String = ""
)