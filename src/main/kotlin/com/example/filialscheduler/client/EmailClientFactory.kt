package com.example.filialscheduler.client

import com.example.filialscheduler.constant.EMAIL_ALARM_SENDER
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Component
class EmailClientFactory(
    private val applicationContext: ApplicationContext,
) {
    fun create(
        to: String,
    ) =
        applicationContext
            .getBean(EMAIL_ALARM_SENDER, EmailClient::class.java)
            .apply { this.to = to }
}