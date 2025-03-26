package com.example.filialscheduler.scheduler

import com.example.filialscheduler.client.Alarm
import com.example.filialscheduler.client.AlarmSender
import com.example.filialscheduler.client.GithubClient
import com.example.filialscheduler.constant.ASIA_SEOUL
import com.example.filialscheduler.constant.DefaultMessage.FAIL_SMS
import com.example.filialscheduler.constant.DefaultMessage.PENALTY_MESSAGE
import com.example.filialscheduler.constant.DefaultMessage.CONGRATULATORY_MESSAGE
import com.example.filialscheduler.constant.EVERYDAY_ONE_MINUTE_PAST_TWELVE_PM
import com.example.filialscheduler.constant.SLACK_ALARM_SENDER
import com.example.filialscheduler.constant.SMS_ALARM_SENDER
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class GithubScheduler(
    private val githubClient: GithubClient,
    @Qualifier(SLACK_ALARM_SENDER) private val slackClient: AlarmSender,
    @Qualifier(SMS_ALARM_SENDER) private val smsClient: AlarmSender,
) {
    @Scheduled(cron = EVERYDAY_ONE_MINUTE_PAST_TWELVE_PM, zone = ASIA_SEOUL)
    suspend fun githubCommit() = coroutineScope {
        val yesterdayCommits = githubClient.getCommitsCountForYesterday()

        if (yesterdayCommits.isZero()) {
            try {
                smsClient.send(
                    Alarm.of(PENALTY_MESSAGE)
                )
            } catch (e: Exception) {
                slackClient.send(
                    Alarm.of(FAIL_SMS)
                )
            }
        } else {
            slackClient.send(
                Alarm.of(CONGRATULATORY_MESSAGE)
            )
        }
    }

    private fun Int.isZero() = this == 0
}
