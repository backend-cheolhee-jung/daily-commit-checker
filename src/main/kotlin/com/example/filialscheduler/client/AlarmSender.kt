package com.example.filialscheduler.client

interface AlarmSender {
    suspend fun send(alarm: Alarm)
}