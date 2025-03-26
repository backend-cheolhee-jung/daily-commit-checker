package com.example.filialscheduler.client

import com.example.filialscheduler.extension.toSlackMessage
import com.example.filialscheduler.property.SlackProperty
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

class SlackClient(
    private val slackProperty: SlackProperty,
): AlarmSender {
    private val webClient = WebClient.create(slackProperty.url)

    override suspend fun send(
        alarm: Alarm,
    ): Unit = coroutineScope {
        launch {
            webClient.post()
                .body(alarm.message.toSlackMessage())
                .retrieve()
                .awaitBody()
        }
    }
}
