package com.example.myapplication.data

import com.example.myapplication.domain.Repository
import com.example.myapplication.domain.models.MacStatus
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.withTimeout
import java.util.Calendar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LockScreenImpl : Repository.LockScreen {


    private val database = Firebase.database
    private val lockRef = database.getReference("general/lock/")


    override suspend fun getStatus() {

    }

    override suspend fun lockScreen(): MacStatus {
        val id = 1
        val time: Long = Calendar.getInstance().timeInMillis
        lockRef.child("$id").setValue("$time")
        lockRef.child("$id/$time").setValue(false)
        return withTimeout(5000) {
            when (getResult(id, time)) {
                null -> {
                    // Таймаут
                    MacStatus.NONE
                }

                true -> {
                    MacStatus.LOCKED
                }

                else -> {
                    MacStatus.NONE
                }
            }
        }
    }


    private suspend fun getResult(id: Int, time: Long): Boolean? {
        return suspendCoroutine { continuation ->
            lockRef.child("$id").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.child("$time").getValue<Boolean>()?.let {
                        if (it) continuation.resume(true)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    continuation.resume(null)
                }
            })
        }
    }
}