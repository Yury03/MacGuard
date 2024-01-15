package com.example.myapplication.ui.home

sealed class HomeEvent {
    data object ButtonIsClicked : HomeEvent()
}