package com.example.ludometricas.data.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notasIndividuais")
class NotaIndividualLocal(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var idJogatina: Int = 0,
    var responsavel: String = "",
    var total: Double = 0.0,
    var mecanica: Double = 0.0,
    var componentes: Double = 0.0,
    var experiencia: Double = 0.0,
    var data: String = ""
)