package com.example.filialscheduler.scheduler

import com.example.filialscheduler.client.Alarm
import com.example.filialscheduler.client.AlarmSender
import com.example.filialscheduler.constant.ASIA_SEOUL
import com.example.filialscheduler.constant.EMAIL_ALARM_SENDER
import com.example.filialscheduler.constant.EVERY_DAY_EIGHT_THIRTY_AM
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailScheduler(
    @Qualifier(EMAIL_ALARM_SENDER) private val emailClient: AlarmSender,
) {
    @Scheduled(cron = EVERY_DAY_EIGHT_THIRTY_AM, zone = ASIA_SEOUL)
    suspend fun sendEmailToRyu() = coroutineScope {
        emailClient.send(
            Alarm.of("화이팅! MR.RYU!!")
        )
    }
}
