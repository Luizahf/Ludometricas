package com.example.ludometricas.modules

import com.example.ludometricas.presentation.jogo.JogoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module



val UIModules = module {

    viewModel {
        JogoViewModel(
            get(),
            get()
        )
    }
}