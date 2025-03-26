package com.example.filialscheduler.client

import com.example.filialscheduler.constant.COOL_SMS_ENDPOINT
import com.example.filialscheduler.property.CherhyProperty
import kotlinx.coroutines.coroutineScope
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.service.DefaultMessageService

class SmsClient(
    private val cherhyProperty: CherhyProperty,
): AlarmSender {
    private val messageService =
        DefaultMessageService(
            cherhyProperty.coolsmsApiKey,
            cherhyProperty.coolsmsApiSecret,
            COOL_SMS_ENDPOINT,
        )

    override suspend fun send(
        alarm: Alarm,
    ): Unit = coroutineScope {
        val message = Message(
            from = cherhyProperty.coolsmsFrom,
            to = cherhyProperty.randomNumber,
            text = alarm.message,
        )

        messageService.sendOne(
            SingleMessageSendingRequest(message)
        )
    }
}
