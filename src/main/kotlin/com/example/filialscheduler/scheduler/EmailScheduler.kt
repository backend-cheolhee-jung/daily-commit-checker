package com.example.filialscheduler.scheduler

import com.example.filialscheduler.client.EmailClientFactory
import com.example.filialscheduler.constant.ASIA_SEOUL
import com.example.filialscheduler.constant.EVERY_DAY_EIGHT_THIRTY_AM
import com.example.filialscheduler.property.RyuProperty
import kotlinx.coroutines.coroutineScope
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class EmailScheduler(
    private val emailClientFactory: EmailClientFactory,
    private val ryuProperty: RyuProperty,
) {
    @Scheduled(cron = EVERY_DAY_EIGHT_THIRTY_AM, zone = ASIA_SEOUL)
    suspend fun sendEmailToRyu() = coroutineScope {
        emailClientFactory.create(ryuProperty.email).send("화이팅! MR.RYU!!")
    }
}
