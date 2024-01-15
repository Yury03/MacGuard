package com.example.myapplication.ui.home

import com.example.myapplication.domain.models.MacStatus

data class HomeUiState(
    val time: String = "",
    val macStatus: MacStatus = MacStatus.NONE
)
