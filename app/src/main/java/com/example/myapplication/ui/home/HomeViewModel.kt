package com.example.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.LockScreenImpl
import com.example.myapplication.domain.models.MacStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    private val lockScreenContract = LockScreenImpl()
    private fun lockScreen() {
        viewModelScope.launch {
            val status = lockScreenContract.lockScreen()
            withContext(Dispatchers.Main) {
                _uiState.update { currentValue ->
                    currentValue.copy(macStatus = status)
                }
            }
        }
    }

    fun sendEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ButtonIsClicked -> {
                _uiState.update { currentValue ->
                    currentValue.copy(macStatus = MacStatus.WAIT)
                }
                lockScreen()
            }
        }
    }
}