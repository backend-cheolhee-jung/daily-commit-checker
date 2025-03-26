package com.example.filialscheduler.client

import com.example.filialscheduler.property.MailProperty
import com.example.filialscheduler.property.RyuProperty
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.mail.javamail.JavaMailSender
import java.io.StringWriter
import kotlin.text.Charsets.UTF_8

class EmailClient(
    private val javaMailSender: JavaMailSender,
    private val mailProperty: MailProperty,
    private val ryuProperty: RyuProperty,
) : AlarmSender {
    override suspend fun send(
        alarm: Alarm,
    ) =
        coroutineScope {
            val writer = StringWriter()
            val msg = writer.appendHTML().html {
                head {
                    style {
                        +"""
                        $CONTENT {
                            font-size: 17px;
                            padding-right: 30px;
                            padding-left: 30px;
                        }
                    """.trimIndent()
                    }
                }

                body {
                    h1(TITLE) {
                        style = "font-size: 30px; padding-right: 30px; padding-left: 30px;"
                        +alarm.message
                    }
                }
            }.toString()

            val internetAddress = withContext(Dispatchers.IO) {
                InternetAddress(mailProperty.username, PERSONAL)
            }
            val message = javaMailSender.createMimeMessage()
                .apply {
                    addRecipients(MimeMessage.RecipientType.TO, ryuProperty.email)
                    subject = alarm.message
                    setText(msg, UTF_8.name(), HTML)
                    setFrom(internetAddress)
                }

            javaMailSender.send(message)
        }

    companion object {
        private const val CONTENT = ".content"
        private const val TITLE = "title"
        private const val PERSONAL = "Cherhy Jung"
        private const val HTML = "html"
    }
}