package com.example.filialscheduler.client

import com.example.filialscheduler.constant.SLACK_ALARM_SENDER
import com.example.filialscheduler.extension.toSlackMessage
import com.example.filialscheduler.property.SlackProperty
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Component(SLACK_ALARM_SENDER)
class SlackClient(
    private val slackProperty: SlackProperty,
) : AlarmSender {
    private val webClient = WebClient.create(slackProperty.url)

    override suspend fun send(
        message: String,
    ): Unit = coroutineScope {
        launch {
            webClient.post()
                .body(message.toSlackMessage())
                .retrieve()
                .awaitBody()
        }
    }
}