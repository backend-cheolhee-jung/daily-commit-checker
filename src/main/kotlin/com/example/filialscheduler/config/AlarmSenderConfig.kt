package com.example.filialscheduler.config

import com.example.filialscheduler.client.EmailClient
import com.example.filialscheduler.client.SlackClient
import com.example.filialscheduler.client.SmsClient
import com.example.filialscheduler.constant.EMAIL_ALARM_SENDER
import com.example.filialscheduler.constant.SLACK_ALARM_SENDER
import com.example.filialscheduler.constant.SMS_ALARM_SENDER
import com.example.filialscheduler.property.CherhyProperty
import com.example.filialscheduler.property.MailProperty
import com.example.filialscheduler.property.RyuProperty
import com.example.filialscheduler.property.SlackProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender

@Configuration
class AlarmSenderConfig(
    private val slackProperty: SlackProperty,
    private val javaMailSender: JavaMailSender,
    private val mailProperty: MailProperty,
    private val ryuProperty: RyuProperty,
    private val cherhyProperty: CherhyProperty,
) {
    @Bean(SLACK_ALARM_SENDER)
    fun slackAlarmSender() =
        SlackClient(slackProperty)

    @Bean(EMAIL_ALARM_SENDER)
    fun emailAlarmSender() =
        EmailClient(javaMailSender, mailProperty, ryuProperty)

    @Bean(SMS_ALARM_SENDER)
    fun smsAlarmSender() =
        SmsClient(cherhyProperty)
}