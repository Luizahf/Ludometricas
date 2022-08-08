package com.example.ludometricas.modules

import org.koin.core.module.Module


object AppModules {
    fun getModules(): List<Module> = listOf(
        UIModules
    )
}