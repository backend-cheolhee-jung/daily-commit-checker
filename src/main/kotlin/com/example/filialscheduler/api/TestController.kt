package com.example.filialscheduler.api

import com.example.filialscheduler.client.AlarmSender
import com.example.filialscheduler.client.BlogClient
import com.example.filialscheduler.client.EmailClientFactory
import com.example.filialscheduler.client.GithubClient
import com.example.filialscheduler.constant.DefaultMessage.CONGRATULATORY_MESSAGE
import com.example.filialscheduler.constant.DefaultMessage.FAIL_SMS
import com.example.filialscheduler.constant.DefaultMessage.PENALTY_MESSAGE
import com.example.filialscheduler.constant.SLACK_ALARM_SENDER
import com.example.filialscheduler.constant.SMS_ALARM_SENDER
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(
    private val githubClient: GithubClient,
    @Qualifier(SLACK_ALARM_SENDER) private val slackClient: AlarmSender,
    @Qualifier(SMS_ALARM_SENDER) private val smsClient: AlarmSender,
    private val emailClientFactory: EmailClientFactory,
    private val blogClient: BlogClient,
) {
    @GetMapping("/github")
    suspend fun github() = githubClient.getCommitsCountForYesterday()

    @GetMapping("/blog")
    suspend fun blog() = blogClient.getLastPostId()

    @PostMapping("/slack/error")
    suspend fun slackFailureTest() = slackClient.send(FAIL_SMS)

    @PostMapping("/slack/success")
    suspend fun slackSuccessTest() = slackClient.send(CONGRATULATORY_MESSAGE)

    @PostMapping("/sms")
    suspend fun smsTest() = smsClient.send(PENALTY_MESSAGE)

    @PostMapping("/email")
    suspend fun emailTest() = emailClientFactory.create(TEST_MAIL).send("이메일 발송 테스트")

    companion object {
        private const val TEST_MAIL = "ekxk1234@gmail.com"
    }
}