package com.example.filialscheduler.scheduler

import com.example.filialscheduler.client.*
import com.example.filialscheduler.component.FileComponent
import com.example.filialscheduler.constant.ASIA_SEOUL
import com.example.filialscheduler.constant.DefaultMessage.PENALTY_MESSAGE
import com.example.filialscheduler.constant.DefaultMessage.CONGRATULATORY_MESSAGE
import com.example.filialscheduler.constant.FIRST_DAY_ONE_MINUTE_PAST_MIDNIGHT
import com.example.filialscheduler.constant.SLACK_ALARM_SENDER
import com.example.filialscheduler.constant.SMS_ALARM_SENDER
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class BlogScheduler(
    private val blogClient: BlogClient,
    private val fileComponent: FileComponent,
    @Qualifier(SLACK_ALARM_SENDER) private val slackClient: AlarmSender,
    @Qualifier(SMS_ALARM_SENDER) private val smsClient: AlarmSender,
) {
    @Scheduled(cron = FIRST_DAY_ONE_MINUTE_PAST_MIDNIGHT, zone = ASIA_SEOUL)
    suspend fun blog(): Unit = coroutineScope {
        val thisMonthBlogId = async { blogClient.getLastPostId() }.await()
        val lastMonthBlogId = async { fileComponent.readLastBlogId() }.await()

        if (thisMonthBlogId != lastMonthBlogId) {
            launch {
                fileComponent.writeLastBlogId(thisMonthBlogId)
            }

            launch {
                try {
                    smsClient.send(
                        Alarm.of(PENALTY_MESSAGE)
                    )
                } catch (e: Exception) {
                    slackClient.send(
                        Alarm.of(CONGRATULATORY_MESSAGE)
                    )
                }
            }
        } else {
            slackClient.send(
                Alarm.of(CONGRATULATORY_MESSAGE)
            )
        }
    }
}
