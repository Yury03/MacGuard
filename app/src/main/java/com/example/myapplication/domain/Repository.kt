package com.example.myapplication.domain

import com.example.myapplication.domain.models.MacStatus

interface Repository {
    interface LockScreen {
        suspend fun getStatus()
        suspend fun lockScreen(): MacStatus
    }
}