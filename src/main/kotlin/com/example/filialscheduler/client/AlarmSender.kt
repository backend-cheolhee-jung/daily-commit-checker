package com.example.filialscheduler.client

interface AlarmSender {
    suspend fun send(message: String)
}